package com.example.imirly.data.model

data class CampoFormulario(
    val id: String,
    val label: String,
    val tipo: String,
    val required: Boolean = false,
    val opciones: List<String> = emptyList()
)
