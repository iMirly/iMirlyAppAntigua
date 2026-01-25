package com.example.imirly.ui.subcategories

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.imirly.data.model.Subcategoria
import com.example.imirly.data.repository.CategoriasRepository

class SubcategoriesViewModel(
    application: Application,
    private val categoryId: String
) : AndroidViewModel(application) {

    private val repository = CategoriasRepository(application)

    var subcategorias = mutableStateOf<List<Subcategoria>>(emptyList())
        private set

    init {
        cargar()
    }

    private fun cargar() {
        subcategorias.value = repository.obtenerSubcategorias(categoryId)
    }
}
