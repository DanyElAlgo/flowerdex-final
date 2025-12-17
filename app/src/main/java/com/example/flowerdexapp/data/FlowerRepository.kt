package com.example.flowerdexapp.data

import android.content.Context
import android.net.Uri
import com.example.flowerdexapp.data.remote.FlorNetwork
import com.example.flowerdexapp.utils.ImageUtils
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FlowerRepository(
    private val context: Context,
    private val dao: FlorDao
) {
    private val supabase = SupabaseClient.client

    suspend fun guardarFlorOnline(florInput: Flor, uriTemporal: Uri) = withContext(Dispatchers.IO) {

        val user = supabase.auth.currentUserOrNull() ?: throw Exception("Usuario no inició sesión")

        val bytes = context.contentResolver.openInputStream(uriTemporal)?.readBytes()
            ?: throw Exception("No se pudo leer la imagen")

        val fileName = "user_${user.id}-${System.currentTimeMillis()}.jpg"
        val bucket = supabase.storage.from("flores-img")
        bucket.upload(fileName, bytes, upsert = true)
        val publicUrl = bucket.publicUrl(fileName)

        val florNetwork = FlorNetwork(
            userId = user.id,
            nombreCientifico = florInput.nombreCientifico,
            nombreComun = florInput.nombreComun,
            familia = florInput.familia,
            descripcion = florInput.descripcion,
            exposicionSolar = florInput.exposicionSolar.name,
            estacionPreferida = florInput.estacionPreferida.name,
            alcalinidadPreferida = florInput.alcalinidadPreferida,
            colores = florInput.colores.map { it.name },
            esToxica = florInput.esToxica,
            fotoUrl = publicUrl
        )

        val florInsertada = supabase.from("flores")
            .insert(florNetwork) { select() }
            .decodeSingle<FlorNetwork>()

        val rutaLocal = ImageUtils.saveImageToInternalStorage(context, uriTemporal)

        val florEntity = florInput.copy(
            id = florInsertada.id ?: 0,
            userId = user.id,
            fotoUri = rutaLocal,
            needsSync = false
        )

        dao.insertar(florEntity)
    }
}