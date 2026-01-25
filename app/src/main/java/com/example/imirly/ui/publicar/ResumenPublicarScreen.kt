package com.example.imirly.ui.publicar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResumenPublicarScreen(
    viewModel: PublicarViewModel,
    navController: NavController,
    onFinish: () -> Unit
) {
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
            Text(
                text = "Resumen del anuncio",
                style = MaterialTheme.typography.titleLarge
            )
        }

        /* ---------- CONTENT ---------- */

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // DATOS GENERALES
            ResumenCard(
                titulo = "Datos generales",
                onEdit = { navController.popBackStack() }
            ) {
                ResumenRow("Categoría", viewModel.categoriaId.value)
                ResumenRow("Subcategoría", viewModel.subcategoriaId.value)
                ResumenRow("Título", viewModel.titulo.value)
                ResumenRow("Ubicación", viewModel.provincia.value)
            }

            // DETALLES
            if (viewModel.valoresFormulario.isNotEmpty()) {
                ResumenCard(titulo = "Detalles del servicio") {
                    viewModel.valoresFormulario.forEach { (k, v) ->
                        ResumenRow(
                            label = k.replace("_", " ").replaceFirstChar { it.uppercase() },
                            value = v.toString()
                        )
                    }
                }
            }
        }

        /* ---------- PUBLICAR ---------- */

        Button(
            onClick = {
                viewModel.publicarAnuncio {
                    onFinish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Publicar anuncio")
        }
    }
}

@Composable
fun ResumenCard(
    titulo: String,
    onEdit: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                if (onEdit != null) {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun ResumenRow(
    label: String,
    value: String
) {
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

