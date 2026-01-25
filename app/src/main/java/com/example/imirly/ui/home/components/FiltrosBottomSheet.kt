package com.example.imirly.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.Formulario


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosBottomSheet(
    formulario: Formulario,
    filtros: Map<String, Any>,
    onCampoChange: (String, Any) -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text("Filtros", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            formulario.campos.forEach { campo ->
                when (campo.tipo) {

                    "boolean" -> {
                        val checked = filtros[campo.id] as? Boolean ?: false

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(campo.label, modifier = Modifier.weight(1f))
                            Switch(
                                checked = checked,
                                onCheckedChange = {
                                    onCampoChange(campo.id, it)
                                }
                            )
                        }
                    }

                    "number" -> {
                        val value = (filtros[campo.id] as? Double)?.toString() ?: ""

                        OutlinedTextField(
                            value = value,
                            onValueChange = {
                                it.toDoubleOrNull()?.let { v ->
                                    onCampoChange(campo.id, v)
                                }
                            },
                            label = { Text(campo.label) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    "select" -> {
                        Row(Modifier.padding(vertical = 8.dp)) {
                            campo.opciones.forEach { opcion ->
                                val selected = filtros[campo.id] == opcion

                                FilterChip(
                                    selected = selected,
                                    onClick = {
                                        onCampoChange(campo.id, opcion)
                                    },
                                    label = { Text(opcion) },
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onApply,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aplicar filtros")
            }
        }
    }
}
