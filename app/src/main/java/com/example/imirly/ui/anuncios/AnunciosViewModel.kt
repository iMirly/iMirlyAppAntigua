package com.example.imirly.ui.anuncios

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import com.example.imirly.data.local.FavoritesStore
import com.example.imirly.data.model.Anuncio
import com.example.imirly.data.model.Formulario
import com.example.imirly.data.repository.CategoriasRepository
import com.example.imirly.data.repository.FormulariosRepository
import com.example.imirly.ui.anuncios.model.AnuncioUi

class AnunciosViewModel(
    application: Application,
    categoriaId: String,
    subcategoriaId: String
) : AndroidViewModel(application) {

    private val anunciosRepository = CategoriasRepository(application)
    private val formulariosRepository = FormulariosRepository(application)
    private val favoritesStore = FavoritesStore(application)

    /** -------- STATE -------- */

    var anuncios by mutableStateOf<List<Anuncio>>(emptyList())
        private set

    var favoritos by mutableStateOf<Set<String>>(emptySet())
        private set

    var mostrarFiltros by mutableStateOf(false)
        private set

    var formulario by mutableStateOf<Formulario?>(null)
        private set

    val filtros: Map<String, Any>
        get() = filtrosActivos
    private val filtrosActivos = mutableStateMapOf<String, Any>()

    /** -------- DERIVED UI STATE -------- */

    val anunciosUi: List<AnuncioUi>
        get() = anuncios.map { anuncio ->
            AnuncioUi(
                anuncio = anuncio,
                tipoPrecio = anuncio.tipoPrecio,
                precio = anuncio.precio,
                rating = listOf(3.8, 4.2, 4.7).random(),
                numServicios = listOf(3, 8, 20).random(),
                favorito = favoritos.contains(anuncio.id),
                online = anuncio.detalles.optBoolean("online", false),
                experiencia = anuncio.detalles.optString("experiencia", "")
            )
        }

    val anunciosFiltrados: List<AnuncioUi>
        get() {
            if (filtrosActivos.isEmpty()) return anunciosUi

            return anunciosUi.filter { item ->
                filtrosActivos.all { (campoId, valor) ->
                    when (campoId) {

                        "precio" -> {
                            val max = valor as Double
                            item.precio?.let { it <= max } ?: true
                        }

                        "online" -> {
                            val requerido = valor as Boolean
                            item.online == requerido
                        }

                        "experiencia" -> {
                            val seleccion = valor as String
                            item.experiencia == seleccion
                        }

                        else -> true
                    }
                }
            }
        }

    /** -------- INIT -------- */

    init {
        anuncios = anunciosRepository.obtenerAnuncios(categoriaId, subcategoriaId)
        favoritos = favoritesStore.getAll().map { it.id }.toSet()
        formulario = formulariosRepository.obtenerFormulario(categoriaId, subcategoriaId)
    }

    /** -------- ACTIONS -------- */

    fun toggleFavorite(anuncio: Anuncio) {
        favoritesStore.toggle(anuncio)
        favoritos = favoritesStore.getAll().map { it.id }.toSet()
    }

    fun abrirFiltros() {
        mostrarFiltros = true
    }

    fun cerrarFiltros() {
        mostrarFiltros = false
    }

    fun aplicarFiltro(campoId: String, valor: Any) {
        filtrosActivos[campoId] = valor
    }

    fun limpiarFiltros() {
        filtrosActivos.clear()
    }
}
