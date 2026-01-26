package com.example.imirly.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.imirly.data.model.TipoPrecio
import com.example.imirly.ui.anuncios.model.AnuncioUi


@Composable
fun AnuncioCard(
    item: AnuncioUi,
    onClick: () -> Unit,
    onFavoritoClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar (igual)
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        item.anuncio.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )

                    item.precio?.let {
                        Text(
                            "${it}€",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    text = "${item.precio}€",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = when (item.tipoPrecio) {
                        "HORA" -> "por hora"
                        "SERVICIO" -> "por servicio"
                        "DIA" -> "por día"
                        "PROYECTO" -> "por proyecto"
                        else -> ""
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )


                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(item.rating.toInt()) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    if (item.rating % 1 >= 0.5) {
                        Icon(
                            Icons.Default.StarHalf,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Text(
                        " ${item.rating} · ${item.numServicios} servicios",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }



                Spacer(Modifier.height(4.dp))

                Text(
                    item.anuncio.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }

            IconButton(onClick = onFavoritoClick) {
                Icon(
                    imageVector = if (item.favorito)
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (item.favorito)
                        Color(0xFFE91E63)
                    else
                        MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
