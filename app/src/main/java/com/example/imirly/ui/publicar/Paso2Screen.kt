package com.example.imirly.ui.publicar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.DiaDisponibilidad
import androidx.navigation.NavController

@Composable
fun Paso2Screen(
    viewModel: PublicarViewModel,
    onPublicar: () -> Unit
) {
    val formulario = viewModel.formulario.value

    Column(modifier = Modifier.fillMaxSize()) {

        /* ---------- HEADER ---------- */

        PasoHeader(
            titulo = "Nuevo anuncio",
            pasoActual = 2,
            totalPasos = 2
        )

        /* ---------- CONTENT ---------- */

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            /* ================= DISPONIBILIDAD ================= */

            item {
                CardBase {
                    Text(
                        text = "Disponibilidad",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Define tu horario de disponibilidad para cada día",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            items(viewModel.diasDisponibilidad.size) { index ->
                DiaDisponibilidadCard(
                    dia = viewModel.diasDisponibilidad[index],
                    onToggle = { viewModel.toggleDia(index, it) },
                    onDesdeChange = { viewModel.cambiarDesde(index, it) },
                    onHastaChange = { viewModel.cambiarHasta(index, it) }
                )
            }

            /* ================= CAMPOS DINÁMICOS ================= */

            if (formulario != null) {
                item {
                    CardBase {
                        DynamicForm(
                            formulario = formulario,
                            valores = viewModel.valoresFormulario,
                            onValueChange = { campoId, valor ->
                                viewModel.onCampoChange(campoId, valor)
                            }
                        )
                    }
                }
            }
        }

        /* ---------- PUBLICAR ---------- */

        Button(
            onClick = {
                if (viewModel.validarPaso2()) {
                    onPublicar()
                }
            },
            enabled = viewModel.validarPaso2(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text("Publicar anuncio")
        }

    }
}



@Composable
fun DiaDisponibilidadCard(
    dia: DiaDisponibilidad,
    onToggle: (Boolean) -> Unit,
    onDesdeChange: (String) -> Unit,
    onHastaChange: (String) -> Unit
) {
    CardBase {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(dia.nombre, modifier = Modifier.weight(1f))
            Switch(
                checked = dia.activo,
                onCheckedChange = onToggle
            )
        }

        Spacer(Modifier.height(8.dp))

        if (dia.activo) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectorHora("Desde", dia.desde, onDesdeChange, Modifier.weight(1f))
                SelectorHora("Hasta", dia.hasta, onHastaChange, Modifier.weight(1f))
            }
        } else {
            Text(
                "No disponible",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorHora(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val horas = listOf(
        "08:00","09:00","10:00","11:00","12:00",
        "13:00","14:00","15:00","16:00","17:00",
        "18:00","19:00","20:00"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(label, style = MaterialTheme.typography.labelSmall)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                horas.forEach { hora ->
                    DropdownMenuItem(
                        text = { Text(hora) },
                        onClick = {
                            expanded = false
                            onValueChange(hora)
                        }
                    )
                }
            }
        }
    }
}
