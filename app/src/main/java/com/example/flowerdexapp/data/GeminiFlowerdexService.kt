package com.example.flowerdexapp.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.flowerdexapp.BuildConfig

class GeminiFlowerdexService {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash", // TODO: Revisar otros posibles modelos
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val gson = Gson()

    private val promptBotanico = """
        Actúa como un experto botánico. Identifica la flor en esta imagen.
        Debes responder ÚNICAMENTE con un objeto JSON válido. No incluyas markdown (```json ... ```), solo el JSON crudo.
        Si no puedes identificar una flor con certeza, devuelve campos vacíos o "Desconocido", pero mantén la estructura JSON.
        
        La estructura JSON debe ser exactamente esta:
        {
          "nombreCientifico": "String",
          "nombreComun": "String",
          "familia": "String",
          "descripcion": "String breve (máx 200 caracteres)",
          "exposicionSolar": "SOL_DIRECTO" o "SEMI_SOMBRA" o "SOMBRA_TOTAL",
          "estacionPreferida": "PRIMAVERA" o "VERANO" o "OTOÑO" o "INVIERNO" o "NINGUNA",
          "alcalinidadPreferida": "String (ej. Ácido, Neutro)",
          "colores": ["ROJO", "AMARILLO"], (Lista de strings usando los nombres de mis Enums: ROJO, MARRON, NARANJA, AMARILLO, VERDE, CELESTE, AZUL, VIOLETA, ROSADO, GRIS, BLANCO),
          "esToxica": boolean
        }
    """.trimIndent()


    suspend fun identificarFlor(context: Context, imageUri: Uri): FlorDto? =
        withContext(Dispatchers.IO) {
            try {
                val bitmap = uriToBitmap(context, imageUri) ?: return@withContext null

                val inputContent = content {
                    image(bitmap)
                    text(promptBotanico)
                }

                val response = generativeModel.generateContent(inputContent)
                val jsonResponse = response.text ?: return@withContext null

                val cleanJson = jsonResponse.replace("```json", "").replace("```", "").trim()

                return@withContext gson.fromJson(cleanJson, FlorDto::class.java)

            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}