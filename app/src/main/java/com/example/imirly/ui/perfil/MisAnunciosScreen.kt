package com.example.imirly.ui.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.imirly.ui.components.MisAnuncioCard

@Composable
fun MisAnunciosScreen(
    navController: NavController
) {
    val vm: MisAnunciosViewModel = viewModel()

    val anuncios = vm.anuncios.value
    val mostrarActivos by vm.mostrarActivos
    val anunciosFiltrados = vm.getAnunciosFiltrados()

    val totalActivos = anuncios.count { it.activo }
    val totalInactivos = anuncios.count { !it.activo }

    var anuncioAEliminar by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {

        /* ---------- HEADER ---------- */

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text("Mis anuncios", style = MaterialTheme.typography.titleLarge)
        }

        /* ---------- FILTROS ---------- */

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterChip(
                selected = mostrarActivos,
                onClick = { vm.cambiarFiltro(true) },
                label = { Text("Activos ($totalActivos)") }
            )
            FilterChip(
                selected = !mostrarActivos,
                onClick = { vm.cambiarFiltro(false) },
                label = { Text("Inactivos ($totalInactivos)") }
            )
        }

        Spacer(Modifier.height(12.dp))

        /* ---------- LISTADO ---------- */

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(anunciosFiltrados.size) { i ->
                val anuncio = anunciosFiltrados[i]

                MisAnuncioCard(
                    anuncio = anuncio,
                    onToggleActivo = { activo ->
                        if (activo) vm.activarAnuncio(anuncio.id)
                        else vm.desactivarAnuncio(anuncio.id)
                    },
                    onDelete = {
                        anuncioAEliminar = anuncio.id
                    },
                    onEdit = {
                        navController.navigate(
                            "editar_anuncio/${anuncio.id}"
                        )
                    }
                )
            }
        }
    }

    /* ---------- DIALOG CONFIRMACIÓN ---------- */

    if (anuncioAEliminar != null) {
        AlertDialog(
            onDismissRequest = { anuncioAEliminar = null },
            title = { Text("Eliminar anuncio") },
            text = {
                Text("¿Seguro que quieres eliminar este anuncio? Esta acción no se puede deshacer.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.borrarAnuncio(anuncioAEliminar!!)
                        anuncioAEliminar = null
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { anuncioAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
