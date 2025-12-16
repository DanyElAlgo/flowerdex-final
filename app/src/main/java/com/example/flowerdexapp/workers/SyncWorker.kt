package com.example.flowerdexapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.flowerdexapp.data.AppDatabase
import com.example.flowerdexapp.data.remote.FlorNetwork
import com.example.flowerdexapp.data.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val database = AppDatabase.getDatabase(applicationContext)
        val dao = database.florDao()
        val supabase = SupabaseClient.client

        val pendientes = dao.obtenerPendientesDeSync()

        if (pendientes.isEmpty()) {
            return@withContext Result.success()
        }

        try {
            pendientes.forEach { flor ->
                var publicUrl: String? = null
                if (flor.fotoUri != null) {
                    val file = File(flor.fotoUri)
                    if (file.exists()) {
                        val bytes = file.readBytes()
                        val fileName = "user_${flor.userId}/${file.name}"
                        val bucket = supabase.storage.from("flores-img")

                        bucket.upload(fileName, bytes, true)
                        publicUrl = bucket.publicUrl(fileName)
                    }
                }

                val florNetwork = FlorNetwork(
                    userId = flor.userId,
                    nombreCientifico = flor.nombreCientifico,
                    nombreComun = flor.nombreComun,
                    familia = flor.familia,
                    descripcion = flor.descripcion,
                    exposicionSolar = flor.exposicionSolar.name,
                    estacionPreferida = flor.estacionPreferida.name,
                    alcalinidadPreferida = flor.alcalinidadPreferida,
                    colores = flor.colores.map { it.name },
                    esToxica = flor.esToxica,
                    fotoUrl = publicUrl
                )

                supabase.from("flores").insert(florNetwork)

                dao.marcarComoSincronizada(flor.id)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}