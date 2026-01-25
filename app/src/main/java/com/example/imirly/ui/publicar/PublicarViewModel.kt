package com.example.imirly.ui.publicar

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imirly.data.local.AnunciosStore
import com.example.imirly.data.model.Categoria
import com.example.imirly.data.model.Formulario
import com.example.imirly.data.model.MiAnuncio
import com.example.imirly.data.model.Subcategoria
import com.example.imirly.data.repository.CategoriasRepository
import com.example.imirly.data.repository.FormulariosRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PublicarViewModel(application: Application) : AndroidViewModel(application) {

    /* ---------------- REPOSITORIES ---------------- */

    private val categoriasRepository = CategoriasRepository(application)
    private val formulariosRepository = FormulariosRepository(application)
    private val anunciosStore = AnunciosStore(application)

    /* ---------------- PASO 1 ---------------- */

    val categorias = mutableStateOf<List<Categoria>>(emptyList())
    val subcategorias = mutableStateOf<List<Subcategoria>>(emptyList())

    var categoriaId = mutableStateOf("")
        private set

    var subcategoriaId = mutableStateOf("")
        private set

    var titulo = mutableStateOf("")

    var descripcion = mutableStateOf("")
    var provincia = mutableStateOf("")

    /* ---------------- PASO 2 ---------------- */

    var formulario = mutableStateOf<Formulario?>(null)
        private set

    val valoresFormulario = mutableStateMapOf<String, Any>()

    /* ---------------- INIT ---------------- */

    init {
        cargarCategorias()
    }

    /* ---------------- CATEGORÍAS ---------------- */

    private fun cargarCategorias() {
        categorias.value = categoriasRepository.obtenerCategorias()
    }

    fun seleccionarCategoria(categoriaSeleccionada: String) {
        categoriaId.value = categoriaSeleccionada
        subcategoriaId.value = ""
        valoresFormulario.clear()

        subcategorias.value =
            categoriasRepository.obtenerSubcategorias(categoriaSeleccionada)
    }

    fun seleccionarSubcategoria(subcategoriaSeleccionada: String) {
        subcategoriaId.value = subcategoriaSeleccionada
    }

    /* ---------------- FORMULARIO ---------------- */

    fun cargarFormulario() {
        formulario.value = formulariosRepository.obtenerFormulario(
            categoriaId.value,
            subcategoriaId.value
        )
        valoresFormulario.clear()
    }

    fun onCampoChange(id: String, value: Any) {
        valoresFormulario[id] = value
    }

    /* ---------------- VALIDACIONES ---------------- */

    fun validarPaso1(): Boolean =
        categoriaId.value.isNotBlank() &&
                subcategoriaId.value.isNotBlank() &&
                titulo.value.isNotBlank() &&
                descripcion.value.isNotBlank() &&
                provincia.value.isNotBlank()


    fun validarPaso2(): Boolean {
        val campos = formulario.value?.campos ?: return false
        return campos.all { campo ->
            !campo.required || valoresFormulario.containsKey(campo.id)
        }
    }

    /* ---------------- PUBLICAR ---------------- */

    fun publicarAnuncio(onFinish: () -> Unit) {
        viewModelScope.launch {

            val anuncio = MiAnuncio(
                id = "", // se genera en el store
                titulo = titulo.value,
                categoria = categoriaId.value,
                subcategoria = subcategoriaId.value,
                precioHora = valoresFormulario["precio"]?.toString() ?: "",
                imagenUrl = null,
                visitas = 0,
                favoritos = 0,
                fecha = fechaActual(),
                activo = true
            )

            anunciosStore.guardarAnuncio(anuncio)

            limpiarFormulario()
            onFinish()
        }
    }

    private fun limpiarFormulario() {
        categoriaId.value = ""
        subcategoriaId.value = ""
        titulo.value = ""
        provincia.value = ""
        valoresFormulario.clear()
        formulario.value = null
    }

    private fun fechaActual(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}
