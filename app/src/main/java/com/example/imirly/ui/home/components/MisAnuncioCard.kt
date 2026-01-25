package com.example.imirly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.MiAnuncio

@Composable
fun MisAnuncioCard(
    anuncio: MiAnuncio,
    onToggleActivo: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            /* ---------- INFO ---------- */

            Text(
                text = anuncio.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "${anuncio.categoria} · ${anuncio.subcategoria}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = anuncio.precioHora,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("👁 ${anuncio.visitas}")
                Text("❤️ ${anuncio.favoritos}")
                Text(anuncio.fecha)
            }

            Spacer(Modifier.height(16.dp))

            Divider()

            Spacer(Modifier.height(12.dp))

            /* ---------- ACTIONS ---------- */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ACTIVO / INACTIVO
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Switch(
                        checked = anuncio.activo,
                        onCheckedChange = { onToggleActivo(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF2ECC71),
                            checkedTrackColor = Color(0xFF2ECC71).copy(alpha = 0.4f)
                        )
                    )

                    Text(
                        text = if (anuncio.activo) "Activo" else "Inactivo",
                        color = if (anuncio.activo)
                            Color(0xFF2ECC71)
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // ICONOS
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
