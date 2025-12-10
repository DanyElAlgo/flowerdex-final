package com.example.flowerdexapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlorDao {
    @Query("SELECT * FROM flores ORDER BY nombreComun ASC")
    fun obtenerTodas(): Flow<List<Flor>> // Flow permite actualizaciones en tiempo real

    @Query("SELECT * FROM flores WHERE id = :id")
    suspend fun obtenerPorId(id: Long): Flor?

    @Insert
    suspend fun insertar(flor: Flor)

    @Delete
    suspend fun eliminar(flor: Flor)
}