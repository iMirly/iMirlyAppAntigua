package com.example.imirly.ui.anuncios.model

import com.example.imirly.data.model.Anuncio


data class AnuncioUi(
    val anuncio: Anuncio,

    val tipoPrecio: String,
    val precio: Double?,

    val rating: Double,
    val numServicios: Int,
    val favorito: Boolean,

    // filtros
    val online: Boolean,
    val experiencia: String
)
