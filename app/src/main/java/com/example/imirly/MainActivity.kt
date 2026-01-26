package com.example.imirly

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.imirly.data.remote.RetrofitClient
import com.example.imirly.ui.components.ImirlyBottomBar
import com.example.imirly.ui.navigation.ImirlyNavHost
import com.example.imirly.ui.navigation.Routes
import com.example.imirly.ui.theme.ImirlyAppTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imirly.ui.anuncios.AnunciosViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //  PRUEBA DE CONEXIÓN AL BACKEND (TEMPORAL)
        /*
        lifecycleScope.launch {
            try {
                val anuncios = RetrofitClient.api.getAnuncios()
                Log.d("API_TEST", "Anuncios recibidos: $anuncios")
            } catch (e: Exception) {
                Log.e("API_TEST", "Error llamando a la API: ${e.message}", e)
            }
        }
        */

        setContent {
            ImirlyAppTheme {
                val navController = rememberNavController()

                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route


                // Rutas SIN BottomBar
                val routesWithoutBottomBar = listOf(
                    Routes.Start.route,
                    Routes.Onboarding.route,
                    Routes.Login.route,
                    Routes.Register.route,
                    Routes.Publicar.route,
                    Routes.PublicarPaso1.route,
                    Routes.PublicarPaso2.route,
                    Routes.PublicarResumen.route
                )

                Scaffold(
                    bottomBar = {
                        if (
                            currentRoute != null &&
                            routesWithoutBottomBar.none { route ->
                                currentRoute.startsWith(route)
                            }
                        ) {
                            ImirlyBottomBar(navController)
                        }
                    }
                ) { innerPadding ->
                    ImirlyNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ImirlyAppTheme {
        // Preview simple sin ViewModel real
    }
}