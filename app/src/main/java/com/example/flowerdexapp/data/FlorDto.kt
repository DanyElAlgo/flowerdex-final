package com.example.flowerdexapp.data

data class FlorDto(
    val nombreCientifico: String?,
    val nombreComun: String?,
    val familia: String?,
    val descripcion: String?,
    val exposicionSolar: String?,
    val estacionPreferida: String?,
    val alcalinidadPreferida: String?,
    val colores: List<String>?,
    val esToxica: Boolean?
)