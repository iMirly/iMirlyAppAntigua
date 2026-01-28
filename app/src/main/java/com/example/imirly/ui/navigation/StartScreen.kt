package com.example.imirly.ui.navigation


import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.imirly.data.local.SessionStore

@Composable
fun StartScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val sessionStore = remember { SessionStore(context) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val isLoggedIn = sessionStore.isLoggedIn()
            
            if (isLoggedIn) {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Start.route) { inclusive = true }
                }
            } else {
                // Si no está logueado, enviamos a Welcome (puedes volver a poner el onboarding aquí luego)
                navController.navigate(Routes.Welcome.route) {
                    popUpTo(Routes.Start.route) { inclusive = true }
                }
            }
        }
    }
}
