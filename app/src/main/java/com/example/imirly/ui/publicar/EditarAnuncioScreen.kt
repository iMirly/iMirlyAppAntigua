package com.example.imirly.ui.publicar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EditarAnuncioScreen(
    anuncioId: String,
    navController: NavController
) {
    val vm: EditarAnuncioViewModel = viewModel(
        factory = EditarAnuncioViewModelFactory(
            navController.context.applicationContext as android.app.Application,
            anuncioId
        )
    )

    val cargando by vm.cargando
    val formulario = vm.formulario.value

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
            Text("Editar anuncio", style = MaterialTheme.typography.titleLarge)
        }

        if (cargando) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = vm.titulo.value,
                onValueChange = { vm.titulo.value = it },
                label = { Text("Título del servicio") },
                modifier = Modifier.fillMaxWidth()
            )

            if (formulario != null) {
                DynamicForm(
                    formulario = formulario,
                    valores = vm.valoresFormulario,
                    onValueChange = { campoId, valor ->
                        vm.valoresFormulario[campoId] = valor
                    }
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    if (vm.validar()) {
                        vm.guardarCambios {
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
