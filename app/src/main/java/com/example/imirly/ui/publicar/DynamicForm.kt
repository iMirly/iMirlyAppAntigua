package com.example.imirly.ui.publicar

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.Formulario


@Composable
fun DynamicForm(
    formulario: Formulario,
    valores: Map<String, Any>,
    onValueChange: (String, Any) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(
            text = formulario.titulo,
            style = MaterialTheme.typography.headlineSmall
        )

        formulario.campos.forEach { campo ->
            when (campo.tipo) {

                "number" -> {
                    OutlinedTextField(
                        value = valores[campo.id]?.toString() ?: "",
                        onValueChange = {
                            onValueChange(campo.id, it)
                        },
                        label = { Text(campo.label) }
                    )
                }

                "boolean" -> {
                    val checked = valores[campo.id] as? Boolean ?: false
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(campo.label)
                        Spacer(Modifier.weight(1f))
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                onValueChange(campo.id, it)
                            }
                        )
                    }
                }

                "checkbox-group" -> {
                    val seleccionados =
                        valores[campo.id] as? Set<String> ?: emptySet()

                    Text(campo.label)

                    campo.opciones.forEach { opcion ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = seleccionados.contains(opcion),
                                onCheckedChange = { checked ->
                                    val nuevo = seleccionados.toMutableSet()
                                    if (checked) nuevo.add(opcion)
                                    else nuevo.remove(opcion)
                                    onValueChange(campo.id, nuevo)
                                }
                            )
                            Text(opcion)
                        }
                    }
                }

            }

        }
    }
}
