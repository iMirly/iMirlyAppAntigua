package com.example.imirly.data.model

data class DiaDisponibilidad(
    val nombre: String,
    val activo: Boolean = false,
    val desde: String = "09:00",
    val hasta: String = "18:00"
)