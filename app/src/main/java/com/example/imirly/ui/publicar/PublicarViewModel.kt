package com.example.imirly.ui.publicar

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.imirly.data.model.Categoria
import com.example.imirly.data.model.DiaDisponibilidad
import com.example.imirly.data.model.Formulario
import com.example.imirly.data.model.Subcategoria
import com.example.imirly.data.repository.CategoriasRepository
import com.example.imirly.data.repository.FormulariosRepository

class PublicarViewModel(
    application: Application
) : AndroidViewModel(application) {

    /* ================= DATOS PERSONALES ================= */

    val nombre = mutableStateOf("")
    val apellidos = mutableStateOf("")
    val dni = mutableStateOf("")
    val telefono = mutableStateOf("")

    /* ================= DATOS DEL SERVICIO ================= */

    val titulo = mutableStateOf("")
    val descripcion = mutableStateOf("")
    val precioHora = mutableStateOf("")
    val ubicacion = mutableStateOf("")

    /* ================= CATEGORÍAS ================= */

    private val categoriasRepository = CategoriasRepository(application)
    private val formulariosRepository = FormulariosRepository(application)

    val categorias = mutableStateOf(emptyList<Categoria>())
    val subcategorias = mutableStateOf<List<Subcategoria>>(emptyList())

    val categoriaId = mutableStateOf("")
    val subcategoriaId = mutableStateOf("")

    /* ================= FORMULARIO DINÁMICO ================= */

    val formulario = mutableStateOf<Formulario?>(null)
    val valoresFormulario = mutableStateMapOf<String, Any>()

    init {
        cargarCategorias()
    }

    private fun cargarCategorias() {
        categorias.value = categoriasRepository.obtenerCategorias()
    }

    fun seleccionarCategoria(id: String) {
        categoriaId.value = id
        subcategoriaId.value = ""
        formulario.value = null
        valoresFormulario.clear()

        subcategorias.value =
            categoriasRepository.obtenerSubcategorias(id)
    }


    fun seleccionarSubcategoria(id: String) {
        subcategoriaId.value = id
    }

    fun cargarFormulario() {
        if (categoriaId.value.isBlank() || subcategoriaId.value.isBlank()) return

        formulario.value = formulariosRepository.obtenerFormulario(
            categoriaId = categoriaId.value,
            subcategoriaId = subcategoriaId.value
        )
    }

    fun onCampoChange(campoId: String, valor: Any) {
        valoresFormulario[campoId] = valor
    }


    /* ================= VALIDACIONES ================= */


    fun validarPaso1(): Boolean {
        return nombre.value.isNotBlank()
                && apellidos.value.isNotBlank()
                && dni.value.isNotBlank()
                && telefono.value.isNotBlank()
                && categoriaId.value.isNotBlank()
                && subcategoriaId.value.isNotBlank()
                && titulo.value.isNotBlank()
                /*&& precioHora.value.isNotBlank()*/
                && ubicacion.value.isNotBlank()
                && descripcion.value.isNotBlank()
    }

    /**
     * Paso 2 es válido si:
     * - al menos un día está activo
     * - los días activos tienen desde < hasta
     * - los campos requeridos del formulario dinámico están rellenos
     */
    fun validarPaso2(): Boolean {

        val hayDiaActivo = diasDisponibilidad.any { it.activo }
        if (!hayDiaActivo) return false

        val horariosValidos = diasDisponibilidad
            .filter { it.activo }
            .all { it.desde < it.hasta }

        if (!horariosValidos) return false

        return formulario.value?.campos?.all { campo ->
            !campo.required || valoresFormulario.containsKey(campo.id)
        } ?: true
    }


    /* ================= PASO 2 - DISPONIBILIDAD ================= */

    val diasDisponibilidad = mutableStateListOf(
        DiaDisponibilidad("Lunes"),
        DiaDisponibilidad("Martes"),
        DiaDisponibilidad("Miércoles"),
        DiaDisponibilidad("Jueves"),
        DiaDisponibilidad("Viernes"),
        DiaDisponibilidad("Sábado"),
        DiaDisponibilidad("Domingo")
    )

    fun toggleDia(index: Int, activo: Boolean) {
        diasDisponibilidad[index] =
            diasDisponibilidad[index].copy(activo = activo)
    }

    fun cambiarDesde(index: Int, hora: String) {
        diasDisponibilidad[index] =
            diasDisponibilidad[index].copy(desde = hora)
    }

    fun cambiarHasta(index: Int, hora: String) {
        diasDisponibilidad[index] =
            diasDisponibilidad[index].copy(hasta = hora)
    }

    /* ================= PUBLICAR ================= */

    fun publicarAnuncio(onFinish: () -> Unit) {
        // Aquí más adelante irá el guardado real (backend / datastore)
        onFinish()
    }
}
