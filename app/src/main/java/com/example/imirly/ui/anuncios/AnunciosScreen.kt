package com.example.imirly.ui.anuncios

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imirly.ui.components.AnuncioCard
import com.example.imirly.ui.home.components.AnunciosHeader
import com.example.imirly.ui.home.components.FiltrosBottomSheet
import com.example.imirly.ui.navigation.Routes
import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnunciosScreen(
    categoryId: String,
    subcategoriaId: String,
    navController: NavHostController
) {
    val context = navController.context
    val application = context.applicationContext as Application

    val vm: AnunciosViewModel = viewModel(
        factory = AnunciosViewModelFactory(
            application = application,
            categoriaId = categoryId,
            subcategoriaId = subcategoriaId
        )
    )

    // ---------- LISTADO + HEADER ----------
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        )
    ) {

        // ---------- HEADER ----------
        item {
            AnunciosHeader(
                titulo = subcategoriaId.replaceFirstChar { it.uppercase() },
                total = vm.anunciosFiltrados.size,
                onBack = { navController.popBackStack() },
                onFiltrosClick = { vm.abrirFiltros() }
            )
        }

        // ---------- LISTA DE ANUNCIOS ----------
        items(vm.anunciosFiltrados) { item ->
            AnuncioCard(
                item = item,
                onClick = {
                    navController.navigate(
                        Routes.DetalleAnuncio.createRoute(item.anuncio.id)
                    )
                },
                onFavoritoClick = {
                    vm.toggleFavorite(item.anuncio)
                }
            )
        }
    }

    // ---------- FILTROS ----------
    if (vm.mostrarFiltros && vm.formulario != null) {
        FiltrosBottomSheet(
            formulario = vm.formulario!!,
            filtros = vm.filtros,
            onCampoChange = { id, valor ->
                vm.aplicarFiltro(id, valor)
            },
            onApply = {
                vm.cerrarFiltros()
            },
            onDismiss = {
                vm.cerrarFiltros()
            }
        )



    }

}
