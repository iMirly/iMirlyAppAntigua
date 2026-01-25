package com.example.imirly.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.ChevronRight

@Composable
fun AyudaScreen(navController: NavHostController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ---------- HEADER ---------- */
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Centro de ayuda",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        /* ---------- BUSCADOR ---------- */
        item {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                enabled = false,
                placeholder = { Text("Buscar artículos de ayuda…") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )
        }

        /* ---------- CATEGORÍAS ---------- */
        item {
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            CategoriesGrid()
        }

        /* ---------- FAQ ---------- */
        item {
            Text(
                text = "Preguntas frecuentes",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            FaqItem(
                question = "¿Cómo puedo contactar con un profesional?",
                tag = "General"
            )
            FaqItem(
                question = "¿Es seguro el pago en la plataforma?",
                tag = "Pagos"
            )
            FaqItem(
                question = "¿Cómo puedo publicar mi servicio?",
                tag = "Profesionales"
            )
            FaqItem(
                question = "¿Puedo cancelar un servicio contratado?",
                tag = "General"
            )
        }

        /* ---------- SOPORTE ---------- */
        item {
            SupportCard(
                onClick = {
                    navController.navigate("perfil/contacto")
                }
            )
        }
    }
}

@Composable
private fun CategoriesGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CategoryCard(
                icon = Icons.Default.PhoneAndroid,
                title = "Primeros pasos",
                subtitle = "Cómo empezar a usar Mirly",
                count = "12 artículos",
                color = Color(0xFFEDE7FF),
                modifier = Modifier.weight(1f)
            )

            CategoryCard(
                icon = Icons.Default.CreditCard,
                title = "Pagos y saldo",
                subtitle = "Gestiona tus transacciones",
                count = "8 artículos",
                color = Color(0xFFE7F6EE),
                modifier = Modifier.weight(1f)
            )
        }


        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CategoryCard(
                icon = Icons.Default.Security,
                title = "Seguridad",
                subtitle = "Mantén tu cuenta segura",
                count = "10 artículos",
                color = Color(0xFFEAF2FF),
                modifier = Modifier.weight(1f)
            )
            CategoryCard(
                icon = Icons.Default.Chat,
                title = "Mensajería",
                subtitle = "Cómo chatear con profesionales",
                count = "6 artículos",
                color = Color(0xFFFFF1E5),
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CategoryCard(
                icon = Icons.Default.Star,
                title = "Valoraciones",
                subtitle = "Sistema de reseñas",
                count = "5 artículos",
                color = Color(0xFFFFF6E0),
                modifier = Modifier.weight(1f)
            )
            CategoryCard(
                icon = Icons.Default.Description,
                title = "Publicar servicios",
                subtitle = "Guía para profesionales",
                count = "15 artículos",
                color = Color(0xFFF3E8FF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CategoryCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    count: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null)
            }

            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
            Text(count, style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
private fun SupportCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.ChatBubbleOutline,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "¿No encuentras lo que buscas?",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Nuestro equipo de soporte está aquí para ayudarte",
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Contactar soporte")
            }
        }
    }
}

@Composable
fun FaqItem(
    question: String,
    tag: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(6.dp))

                AssistChip(
                    onClick = {},
                    label = { Text(tag) }
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null
            )
        }
    }
}

