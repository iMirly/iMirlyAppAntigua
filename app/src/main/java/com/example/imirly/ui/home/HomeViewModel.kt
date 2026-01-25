import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.imirly.data.model.Categoria
import com.example.imirly.data.repository.CategoriasRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CategoriasRepository(application)

    private val todasLasCategorias = repository.obtenerCategorias()

    var searchQuery = mutableStateOf("")
        private set

    var categorias = mutableStateOf<List<Categoria>>(todasLasCategorias)
        private set

    fun onSearchChange(text: String) {
        searchQuery.value = text

        categorias.value =
            if (text.isBlank()) {
                todasLasCategorias
            } else {
                todasLasCategorias.filter { categoria ->
                    repository.categoriaCoincideConBusqueda(
                        categoria = categoria,
                        texto = text
                    )
                }
            }
    }
}
