package com.example.imirly.ui.publicar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditarAnuncioViewModelFactory(
    private val application: Application,
    private val anuncioId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarAnuncioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditarAnuncioViewModel(
                application = application,
                anuncioId = anuncioId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
