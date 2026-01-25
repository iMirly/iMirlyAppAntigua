package com.example.imirly.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.imirly.ui.navigation.Routes

@Composable
fun ContactoScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ---------- HEADER ---------- */
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Contacta con nosotros",
                style = MaterialTheme.typography.titleLarge
            )
        }

        /* ---------- TEXTO ---------- */
        Text(
            text = "Estamos aquí para ayudarte. Elige la opción que prefieras para ponerte en contacto con el equipo de Mirly.",
            style = MaterialTheme.typography.bodyMedium
        )

        /* ---------- OPCIONES ---------- */
        ContactOption(
            icon = Icons.Default.Chat,
            title = "Chat con Mirly",
            subtitle = "Respuestas rápidas y soporte inmediato"
        ) {
            navController.navigate(Routes.ChatMirly.route)
        }

        ContactOption(
            icon = Icons.Default.Email,
            title = "Email",
            subtitle = "soporte@imirly.com"
        ) {}

        ContactOption(
            icon = Icons.Default.Call,
            title = "Teléfono",
            subtitle = "+34 900 123 456"
        ) {}

        /* ---------- HORARIO ---------- */
        Spacer(Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Horario de atención",
                    fontWeight = FontWeight.Bold
                )
                Text("Lunes a viernes")
                Text("09:00 – 18:00")
            }
        }
    }
}

@Composable
private fun ContactOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
