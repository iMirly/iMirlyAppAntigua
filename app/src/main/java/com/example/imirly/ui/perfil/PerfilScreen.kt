package com.example.imirly.ui.perfil


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imirly.data.local.SessionStore
import com.example.imirly.data.model.UserProfile
import com.example.imirly.ui.navigation.Routes
import com.google.rpc.Help
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val sessionStore = remember { SessionStore(context) }

    val scope = rememberCoroutineScope()

    val profile by sessionStore.userProfileFlow.collectAsState(
        initial = UserProfile()
    )

    // 🔠 NOMBRE
    val userName = profile.name

    // 🔠 INICIALES (2 letras)
    val initials = userName
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifBlank { "?" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {

        item {
            // ---------- HEADER ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = initials,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = userName.ifBlank { "—" },
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(4) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Icon(Icons.Default.StarBorder, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("(24)", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }


        item { PerfilSectionSpacer() }

        item {
            PerfilItem("Mis anuncios", Icons.Default.Description) {
                navController.navigate(Routes.MisAnuncios.route)
            }
        }

        item {
            PerfilItem("Saldo", Icons.Default.AccountBalanceWallet) {
                navController.navigate(Routes.Saldo.route)
            }
        }

        item { PerfilSectionSpacer() }

        item {
            PerfilItem("Datos personales", Icons.Default.Shield) {
                navController.navigate(Routes.DatosPersonales.route)
            }
        }

        item {
            PerfilItem("Cambiar contraseña", Icons.Default.Lock) {
                navController.navigate(Routes.CambiarPassword.route)
            }
        }

        item { PerfilSectionSpacer() }

        item {
            PerfilItem("Chat de Mirly", Icons.Default.Chat) {
                navController.navigate(Routes.ChatMirly.route)
            }
        }

        item {
            PerfilItem("Sobre Mirly", Icons.Default.Info) {
                navController.navigate(Routes.SobreMirly.route)
            }
        }

        item { PerfilSectionSpacer() }

        item {
            PerfilItem("Ayuda", Icons.Default.Help) {
                navController.navigate(Routes.Ayuda.route)
            }
        }

        item {
            PerfilItem("Contacta con nosotros", Icons.Default.Mail) {
                navController.navigate(Routes.Contacto.route)
            }
        }

        item { PerfilSectionSpacer() }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.error.copy(alpha = 0.05f)
                    )
                    .clickable {
                        scope.launch {
                            sessionStore.logout()
                            navController.navigate(Routes.Start.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "Cerrar sesión",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun PerfilItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun PerfilSectionSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .background(
                MaterialTheme.colorScheme.outline.copy(alpha = 0.08f)
            )
    )
}

