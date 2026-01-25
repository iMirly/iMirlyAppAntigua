package com.example.imirly.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.Categoria

@Composable
fun CategoryCard(
    categoria: Categoria,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = categoria.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${categoria.totalProfesionales} profesionales",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
