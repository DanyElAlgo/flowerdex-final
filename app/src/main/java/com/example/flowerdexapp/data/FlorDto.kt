package com.example.flowerdexapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlorDto(
    val nombreCientifico: String?,
    val nombreComun: String?,
    @SerialName("created_at")
    val createdAt: String?,
    val familia: String?,
    val descripcion: String?,
    val exposicionSolar: String?,
    val estacionPreferida: String?,
    val alcalinidadPreferida: String?,
    val colores: List<String>?,
    val esToxica: Boolean?
)