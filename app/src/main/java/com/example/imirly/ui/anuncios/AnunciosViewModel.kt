package com.example.imirly.ui.anuncios

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imirly.data.local.FavoritesStore
import com.example.imirly.data.model.Anuncio
import com.example.imirly.data.model.Formulario
import com.example.imirly.data.repository.AnunciosRemoteRepository
import com.example.imirly.data.repository.CategoriasRepository
import com.example.imirly.data.repository.FormulariosRepository
import com.example.imirly.ui.anuncios.model.AnuncioUi
import kotlinx.coroutines.launch
import org.json.JSONObject

class AnunciosViewModel(
    application: Application,
    private val categoriaId: String,
    private val subcategoriaId: String
) : AndroidViewModel(application) {

    // ---------- REPOSITORIES ----------
    private val anunciosRepository = CategoriasRepository(application) // JSON LOCAL
    private val formulariosRepository = FormulariosRepository(application)
    private val favoritesStore = FavoritesStore(application)
    private val remoteRepository = AnunciosRemoteRepository() // BACKEND

    /** -------- STATE -------- */

    var anuncios by mutableStateOf<List<Anuncio>>(emptyList())
        private set

    var favoritos by mutableStateOf<Set<String>>(emptySet())
        private set

    var mostrarFiltros by mutableStateOf(false)
        private set

    var formulario by mutableStateOf<Formulario?>(null)
        private set

    private val filtrosActivos = mutableStateMapOf<String, Any>()
    val filtros: Map<String, Any>
        get() = filtrosActivos

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
        favoritos = favoritesStore.getAll().map { it.id }.toSet()
        formulario = formulariosRepository.obtenerFormulario(categoriaId, subcategoriaId)

        cargarAnunciosDesdeBackend()
    }

    /** -------- BACKEND -------- */

    private fun cargarAnunciosDesdeBackend() {
        viewModelScope.launch {
            try {
                val anunciosDto = remoteRepository.getAnuncios()

                Log.d("VM_BACKEND", "Anuncios desde backend: $anunciosDto")

                anuncios = anunciosDto.map { dto ->
                    Anuncio(
                        id = dto.id.toString(),

                        // valores temporales hasta que backend los mande
                        categoria = "servicios",
                        subcategoria = "general",
                        nombre = dto.titulo,

                        provincia = dto.provincia,
                        localidad = dto.localidad,

                        titulo = dto.titulo,
                        descripcion = dto.descripcion,

                        tipoPrecio = dto.priceType, // STRING
                        precio = dto.price,

                        detalles = JSONObject(dto.detallesJson ?: "{}")
                    )
                }

            } catch (e: Exception) {
                Log.e("VM_BACKEND", "Error cargando anuncios backend", e)

                // fallback a JSON local
                anuncios = anunciosRepository.obtenerAnuncios(categoriaId, subcategoriaId)
            }
        }
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
