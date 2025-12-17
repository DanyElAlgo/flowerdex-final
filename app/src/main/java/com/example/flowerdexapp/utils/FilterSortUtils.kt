package com.example.flowerdexapp.utils

enum class SortOption(val label: String) {
    FECHA("Fecha de avistamiento"),
    NOMBRE("Nombre común"),
    COLOR("Color principal"),
    ALCALINIDAD("Alcalinidad"),
    TIPO_ESTACION("Estación preferida")
}

data class FlowerFilterState(
    val sortBy: SortOption = SortOption.FECHA,
    val isAscending: Boolean = true,
    val hideToxic: Boolean = false,
    val hideSunOnly: Boolean = false,
    val hideShadeOnly: Boolean = false
)