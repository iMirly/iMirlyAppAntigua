package com.example.imirly.ui.anuncios

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetalleAnuncioViewModelFactory(
    private val application: Application,
    private val anuncioId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetalleAnuncioViewModel(application, anuncioId) as T
    }
}
