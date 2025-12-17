package com.example.flowerdexapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlorNetwork(
    val id: Long? = null,
    @SerialName("user_id") val userId: String,
    @SerialName("nombre_cientifico") val nombreCientifico: String,
    @SerialName("nombre_comun") val nombreComun: String,
    @SerialName("familia") val familia: String,
    @SerialName("descripcion") val descripcion: String?,
    @SerialName("exposicion_solar") val exposicionSolar: String,
    @SerialName("estacion_preferida") val estacionPreferida: String,
    @SerialName("alcalinidad_preferida") val alcalinidadPreferida: String,
    @SerialName("colores") val colores: List<String>,
    @SerialName("es_toxica") val esToxica: Boolean,
    @SerialName("foto_url") val fotoUrl: String?
)