package com.example.flowerdexapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // --- Convertidores de Enums ---
    @TypeConverter
    fun fromTipoExposicion(value: TipoExposicion): String = value.name
    @TypeConverter
    fun toTipoExposicion(value: String): TipoExposicion = TipoExposicion.valueOf(value)

    @TypeConverter
    fun fromTipoEstacion(value: TipoEstacion): String = value.name
    @TypeConverter
    fun toTipoEstacion(value: String): TipoEstacion = TipoEstacion.valueOf(value)

    // --- Convertidor para la lista de colores ---
    @TypeConverter
    fun fromColoresList(value: List<TipoColor>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toColoresList(value: String): List<TipoColor> {
        val listType = object : TypeToken<List<TipoColor>>() {}.type
        return gson.fromJson(value, listType)
    }
}