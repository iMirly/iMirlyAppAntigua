package com.example.imirly.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imirly.data.local.SessionStore
import com.example.imirly.ui.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun DatosPersonalesScreen(
    navController: NavHostController,
    viewModel: DatosPersonalesViewModel = viewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val context = LocalContext.current
    val sessionStore = remember { SessionStore(context) }
    val scope = rememberCoroutineScope()

    var showDeleteDialog by remember { mutableStateOf(false) }

    val initials = profile.name
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifBlank { "?" }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar cuenta") },
            text = {
                Text("Esta acción eliminará tu cuenta y todos tus datos. No se puede deshacer.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            sessionStore.deleteAccount()

                            navController.navigate(Routes.Start.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 120.dp
        )
    ) {

        item {

            /* ---------- HEADER ---------- */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Outlined.ArrowBack, contentDescription = null)
                }

                Text(
                    text = "Datos personales",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Editar",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.DatosPersonales.route + "/editar")
                    }
                )
            }

            Spacer(Modifier.height(8.dp))

            /* ---------- FOTO PERFIL ---------- */
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .shadow(6.dp, CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Foto de perfil",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ---------- INFO PERSONAL ---------- */
            InfoCard(title = "Información personal") {
                InfoRow(
                    icon = Icons.Outlined.Person,
                    label = "Nombre completo",
                    value = profile.name.ifBlank { "—" }
                )
                InfoRow(
                    icon = Icons.Outlined.Email,
                    label = "Email",
                    value = profile.email.ifBlank { "—" }
                )
                InfoRow(
                    icon = Icons.Outlined.Phone,
                    label = "Teléfono",
                    value = profile.phone.ifBlank { "—" }
                )
                InfoRow(
                    icon = Icons.Outlined.Cake,
                    label = "Fecha de nacimiento",
                    value = profile.birthdate.ifBlank { "—" }
                )
            }

            Spacer(Modifier.height(16.dp))

            /* ---------- DIRECCIÓN ---------- */
            InfoCard(title = "Dirección") {

                InfoRow(
                    icon = Icons.Outlined.LocationOn,
                    label = "Calle y número",
                    value = profile.address.ifBlank { "—" }
                )

                RememberedRowSpaceBetween(
                    leftLabel = "Ciudad",
                    leftValue = profile.city.ifBlank { "—" },
                    rightLabel = "C.P.",
                    rightValue = profile.postalCode.ifBlank { "—" }
                )
            }

            Spacer(Modifier.height(16.dp))

            /* ---------- ELIMINAR CUENTA ---------- */
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                val context = LocalContext.current
                val sessionStore = remember { SessionStore(context) }
                val scope = rememberCoroutineScope()

                Text(
                    text = "Eliminar cuenta",
                    color = Color.Red,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showDeleteDialog = true
                        }
                        .padding(20.dp),
                    textAlign = TextAlign.Center
                )

            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

/* ---------- COMPONENTES ---------- */

@Composable
private fun InfoCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = value,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun RememberedRowSpaceBetween(
    leftLabel: String,
    leftValue: String,
    rightLabel: String,
    rightValue: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = leftLabel,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = leftValue,
                fontWeight = FontWeight.Medium
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = rightLabel,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = rightValue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
