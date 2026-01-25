package com.example.imirly.ui.publicar

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imirly.data.local.AnunciosStore
import com.example.imirly.data.model.Formulario
import com.example.imirly.data.model.MiAnuncio
import com.example.imirly.data.repository.FormulariosRepository
import kotlinx.coroutines.launch

class EditarAnuncioViewModel(
    application: Application,
    private val anuncioId: String
) : AndroidViewModel(application) {

    private val anunciosStore = AnunciosStore(application)
    private val formulariosRepository = FormulariosRepository(application)

    /* ---------- ESTADO ---------- */

    var anuncioOriginal = mutableStateOf<MiAnuncio?>(null)
        private set

    var titulo = mutableStateOf("")
    var provincia = mutableStateOf("")
    var categoriaId = mutableStateOf("")
    var subcategoriaId = mutableStateOf("")

    var formulario = mutableStateOf<Formulario?>(null)
        private set

    val valoresFormulario = mutableStateMapOf<String, Any>()

    var cargando = mutableStateOf(true)
        private set

    /* ---------- INIT ---------- */

    init {
        cargarAnuncio()
    }

    private fun cargarAnuncio() {
        viewModelScope.launch {

            val anuncio = anunciosStore.getAnuncioById(anuncioId)
            anuncioOriginal.value = anuncio

            if (anuncio != null) {
                titulo.value = anuncio.titulo
                categoriaId.value = anuncio.categoria
                subcategoriaId.value = anuncio.subcategoria
                provincia.value = "" // si luego la guardas, aquí se rellena

                cargarFormulario()
            }

            cargando.value = false
        }
    }

    /* ---------- FORMULARIO ---------- */

    private fun cargarFormulario() {
        formulario.value = formulariosRepository.obtenerFormulario(
            categoriaId.value,
            subcategoriaId.value
        )
        valoresFormulario.clear()
    }

    /* ---------- VALIDACIÓN ---------- */

    fun validar(): Boolean =
        titulo.value.isNotBlank() &&
                categoriaId.value.isNotBlank() &&
                subcategoriaId.value.isNotBlank()

    /* ---------- GUARDAR ---------- */

    fun guardarCambios(onFinish: () -> Unit) {
        val original = anuncioOriginal.value ?: return

        viewModelScope.launch {

            val anuncioEditado = original.copy(
                titulo = titulo.value,
                categoria = categoriaId.value,
                subcategoria = subcategoriaId.value,
                precioHora = original.precioHora // luego editable si quieres
            )

            anunciosStore.actualizarAnuncio(anuncioEditado)
            onFinish()
        }
    }
}
