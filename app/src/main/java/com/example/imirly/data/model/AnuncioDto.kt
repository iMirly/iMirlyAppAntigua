package com.example.imirly.data.model

data class AnuncioDto(
    val id: Long,
    val titulo: String,
    val descripcion: String,
    val provincia: String,
    val localidad: String,
    val price: Double,
    val priceType: String,
    val diasDisponibles: List<String>,
    val detalles: String,
    val detallesJson: String?
)
