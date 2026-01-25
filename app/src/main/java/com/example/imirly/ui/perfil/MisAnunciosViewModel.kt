package com.example.imirly.ui.perfil

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imirly.data.local.AnunciosStore
import com.example.imirly.data.model.MiAnuncio
import kotlinx.coroutines.launch

class MisAnunciosViewModel(application: Application) : AndroidViewModel(application) {

    private val anunciosStore = AnunciosStore(application)

    var mostrarActivos = mutableStateOf(true)
        private set

    var anuncios = mutableStateOf<List<MiAnuncio>>(emptyList())
        private set

    init {
        cargarAnuncios()
    }

    /* ---------- LOAD ---------- */

    private fun cargarAnuncios() {
        viewModelScope.launch {
            anuncios.value = anunciosStore.getAnuncios()
        }
    }

    /* ---------- FILTER ---------- */

    fun getAnunciosFiltrados(): List<MiAnuncio> =
        anuncios.value.filter { it.activo == mostrarActivos.value }

    fun cambiarFiltro(activos: Boolean) {
        mostrarActivos.value = activos
    }

    /* ---------- ACTIONS ---------- */

    fun desactivarAnuncio(anuncioId: String) {
        viewModelScope.launch {
            anunciosStore.cambiarEstadoAnuncio(anuncioId, false)
            cargarAnuncios()
        }
    }

    fun activarAnuncio(anuncioId: String) {
        viewModelScope.launch {
            anunciosStore.cambiarEstadoAnuncio(anuncioId, true)
            cargarAnuncios()
        }
    }

    fun borrarAnuncio(anuncioId: String) {
        viewModelScope.launch {
            anunciosStore.borrarAnuncio(anuncioId)
            cargarAnuncios()
        }
    }
}
