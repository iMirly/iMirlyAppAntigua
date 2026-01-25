package com.example.imirly.ui.anuncios

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.imirly.data.model.Anuncio
import com.example.imirly.data.model.Formulario
import com.example.imirly.data.repository.CategoriasRepository
import com.example.imirly.data.repository.FormulariosRepository

class DetalleAnuncioViewModel(
    application: Application,
    anuncioId: String
) : AndroidViewModel(application) {

    private val categoriasRepository = CategoriasRepository(application)
    private val formulariosRepository = FormulariosRepository(application)

    var anuncio by mutableStateOf<Anuncio?>(null)
        private set

    var formulario by mutableStateOf<Formulario?>(null)
        private set

    init {
        val encontrado = categoriasRepository.obtenerAnuncioPorId(anuncioId)
        anuncio = encontrado

        encontrado?.let {
            formulario = formulariosRepository.obtenerFormulario(
                it.categoria,
                it.subcategoria
            )
        }
    }
}

