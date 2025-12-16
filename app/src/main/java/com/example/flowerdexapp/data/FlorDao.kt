package com.example.flowerdexapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlorDao {
    @Query("SELECT * FROM flores ORDER BY nombreComun ASC")
    fun obtenerTodas(): Flow<List<Flor>>

    @Query("SELECT * FROM flores WHERE id = :id")
    fun obtenerFlorPorId(id: Long): Flow<Flor?>

    @Insert
    suspend fun insertar(flor: Flor)

    @Query("SELECT * FROM flores WHERE needsSync = 1")
    suspend fun obtenerPendientesDeSync(): List<Flor>

    @Query("UPDATE flores SET needsSync = 0 WHERE id = :id")
    suspend fun marcarComoSincronizada(id: Long)

    // TODO: Implementar función para eliminar una flor de la base de datos
}