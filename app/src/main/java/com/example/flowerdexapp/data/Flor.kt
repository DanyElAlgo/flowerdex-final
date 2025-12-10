package com.example.flowerdexapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flores")
data class Flor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombreCientifico: String,
    val nombreComun: String,
    val familia: String,
    val descripcion: String? = null,

    val exposicionSolar: TipoExposicion,
    val frecuenciaRiego: Int,
    val estacionPreferida: TipoEstacion,
    val alcalinidadPreferida: String,
    val colores: List<TipoColor>,
    val esToxica: Boolean,

    val fechaAvistamiento: Long? = null,
    val fotoUri: String? = null
)

enum class TipoExposicion(val descripcion: String) {
    SOL_DIRECTO("Sol directo"),
    SEMI_SOMBRA("Semi sombra"),
    SOMBRA_TOTAL("Sombra total")
}
enum class TipoEstacion(val descripcion: String) {
    PRIMAVERA("Primavera"),
    VERANO("Verano"),
    OTOÑO("Otoño"),
    INVIERNO("Invierno")
}
enum class TipoColor(val descripcion: String) {
    ROJO("Rojo"), 
    MARRON("Marron"),
    NARANJA("Naranja"),
    AMARILLO("Amarillo"),
    VERDE("Verde"),
    CELESTE("Celeste"),
    AZUL("Azul"),
    VIOLETA("Violeta"),
    ROSADO("Rosado"),
    GRIS("Gris"),
    BLANCO("Blanco")
}