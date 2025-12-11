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
    suspend fun obtenerPorId(id: Long): Flor?

    @Query("SELECT * FROM flores WHERE id = :id")
    fun obtenerFlorPorId(id: Long): Flow<Flor?>

    @Insert
    suspend fun insertar(flor: Flor)

    @Delete
    suspend fun eliminar(flor: Flor)
}