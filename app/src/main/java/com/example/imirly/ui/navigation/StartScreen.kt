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
            val onboardingDone = sessionStore.isOnboardingDone()

            when {
                isLoggedIn -> {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Start.route) { inclusive = true }
                    }
                }

                onboardingDone -> {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Start.route) { inclusive = true }
                    }
                }

                else -> {
                    navController.navigate(Routes.Onboarding.route) {
                        popUpTo(Routes.Start.route) { inclusive = true }
                    }
                }
            }
        }
    }
}
