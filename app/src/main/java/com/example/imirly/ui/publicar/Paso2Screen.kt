package com.example.imirly.ui.publicar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Paso2Screen(
    viewModel: PublicarViewModel,
    navController: NavController,
    onContinue: () -> Unit
) {
    val formulario = viewModel.formulario.value

    if (formulario == null) {
        Text(
            text = "Cargando formulario...",
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {

        /* ---------- HEADER ---------- */

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }
            Text(
                text = formulario.titulo,
                style = MaterialTheme.typography.titleLarge
            )
        }

        /* ---------- FORM ---------- */

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                DynamicForm(
                    formulario = formulario,
                    valores = viewModel.valoresFormulario,
                    onValueChange = { campoId, valor ->
                        viewModel.onCampoChange(campoId, valor)
                    }
                )
            }
        }

        /* ---------- CONTINUAR ---------- */

        Button(
            onClick = {
                if (viewModel.validarPaso2()) {
                    onContinue()
                }
            },
            enabled = viewModel.validarPaso2(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp)
        ) {
            Text("Continuar")
        }
    }
}
