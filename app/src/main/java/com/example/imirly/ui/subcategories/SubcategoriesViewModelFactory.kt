package com.example.imirly.ui.subcategories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubcategoriesViewModelFactory(
    private val application: Application,
    private val categoryId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubcategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubcategoriesViewModel(application, categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
