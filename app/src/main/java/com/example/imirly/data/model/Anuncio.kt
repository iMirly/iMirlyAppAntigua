package com.example.imirly.data.model

import org.json.JSONObject


data class Anuncio(
    val id: String,
    val categoria: String,
    val subcategoria: String,
    val nombre: String,
    val provincia: String,
    val titulo: String,
    val descripcion: String,
    val tipoPrecio: String,
    val precio: Double?,
    val detalles: JSONObject,
    val localidad: String
)
