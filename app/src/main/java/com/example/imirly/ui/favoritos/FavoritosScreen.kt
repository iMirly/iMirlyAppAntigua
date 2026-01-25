package com.example.imirly.ui.favoritos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.imirly.data.local.FavoritesStore
import com.example.imirly.ui.anuncios.model.AnuncioUi
import com.example.imirly.ui.components.AnuncioCard
import com.example.imirly.ui.navigation.Routes

@Composable
fun FavoritosScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val store = remember { FavoritesStore(context) }

    var favoritos by remember { mutableStateOf(store.getAll()) }

    // 🔹 Convertimos Anuncio → AnuncioUi
    val favoritosUi = favoritos.map { anuncio ->
        AnuncioUi(
            anuncio = anuncio,
            rating = 4.5,
            numServicios = 10,
            favorito = true,

            // filtros / info mock (por ahora)
            online = false,
            experiencia = "Senior",

            //  precio REAL desde el anuncio
            precio = anuncio.precio ?: 0.0,
            tipoPrecio = anuncio.tipoPrecio
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(favoritosUi) { item ->
            AnuncioCard(
                item = item,
                onClick = {
                    navController.navigate(
                        Routes.DetalleAnuncio.createRoute(item.anuncio.id)
                    )
                },
                onFavoritoClick = {
                    store.toggle(item.anuncio)
                    favoritos = store.getAll() //  refresca lista
                }
            )
        }
    }
}
