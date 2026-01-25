package com.example.imirly.data.model

data class Formulario(
    val titulo: String,
    val campos: List<CampoFormulario>
)

data class FormularioCampo(
    val id: String,
    val label: String,
    val tipo: String,
    val required: Boolean = false,
    val opciones: List<String> = emptyList()
)

