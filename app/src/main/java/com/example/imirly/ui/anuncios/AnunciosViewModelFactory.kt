package com.example.imirly.ui.anuncios

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnunciosViewModelFactory(
    private val application: Application,
    private val categoriaId: String,
    private val subcategoriaId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnunciosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnunciosViewModel(
                application,
                categoriaId,
                subcategoriaId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
