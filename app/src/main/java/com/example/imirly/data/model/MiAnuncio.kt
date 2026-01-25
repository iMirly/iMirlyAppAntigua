package com.example.imirly.data.model

data class MiAnuncio(
    val id: String,
    val titulo: String,
    val categoria: String,
    val subcategoria: String,
    val precioHora: String,
    val imagenUrl: String? = null,
    val visitas: Int,
    val favoritos: Int,
    val fecha: String,
    val activo: Boolean
)
