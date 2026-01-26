package com.example.imirly.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.imirly.ui.anuncios.AnunciosScreen
import com.example.imirly.ui.anuncios.DetalleAnuncioScreen
import com.example.imirly.ui.home.HomeScreen
import com.example.imirly.ui.publicar.Paso1Screen
import com.example.imirly.ui.publicar.Paso2Screen
import com.example.imirly.ui.publicar.PublicarViewModel
import com.example.imirly.ui.publicar.ResumenPublicarScreen
import com.example.imirly.ui.subcategories.SubcategoriesScreen
import com.example.imirly.ui.onboarding.OnboardingScreen
import com.example.imirly.ui.auth.LoginScreen
import com.example.imirly.ui.auth.RegisterScreen
import com.example.imirly.ui.chat.ChatMirlyScreen
import com.example.imirly.ui.favoritos.FavoritosScreen
import com.example.imirly.ui.perfil.AyudaScreen
import com.example.imirly.ui.perfil.CambiarPasswordScreen
import com.example.imirly.ui.perfil.ContactoScreen
import com.example.imirly.ui.perfil.DatosPersonalesScreen
import com.example.imirly.ui.perfil.EditarDatosPersonalesScreen
import com.example.imirly.ui.perfil.MisAnunciosScreen
import com.example.imirly.ui.perfil.PerfilPlaceholderScreen
import com.example.imirly.ui.perfil.PerfilScreen
import com.example.imirly.ui.perfil.SaldoScreen
import com.example.imirly.ui.perfil.SobreMirlyScreen
import com.example.imirly.ui.publicar.EditarAnuncioScreen

@Composable
fun ImirlyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val publicarViewModel: PublicarViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Start.route,
        modifier = modifier
    ) {

        /* ---------------- START ---------------- */

        composable(Routes.Start.route) {
            StartScreen(navController)
        }

        /* ---------------- ONBOARDING ---------------- */

        composable(Routes.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- AUTH ---------------- */

        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Routes.Register.route)
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Register.route) { inclusive = true }
                    }
                },
                onGoToLogin = {
                    navController.popBackStack()
                }
            )
        }

        /* ---------------- HOME ---------------- */

        composable(Routes.Home.route) {
            HomeScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(
                        Routes.Subcategories.createRoute(categoryId)
                    )
                }
            )
        }

        /* ---------------- SUBCATEGORÍAS ---------------- */

        composable(Routes.Subcategories.route) { backStackEntry ->
            val categoryId =
                backStackEntry.arguments?.getString("categoryId") ?: return@composable

            SubcategoriesScreen(
                categoryId = categoryId,
                navController = navController
            )
        }

        /* ---------------- ANUNCIOS ---------------- */

        composable(Routes.Anuncios.route) { backStackEntry ->
            val categoryId =
                backStackEntry.arguments?.getString("categoryId") ?: return@composable
            val subcategoriaId =
                backStackEntry.arguments?.getString("subcategoriaId") ?: return@composable

            AnunciosScreen(
                categoryId = categoryId,
                subcategoriaId = subcategoriaId,
                navController = navController
            )
        }

        composable(
            route = Routes.DetalleAnuncio.route,
            arguments = listOf(navArgument("anuncioId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val anuncioId =
                backStackEntry.arguments?.getString("anuncioId")!!

            DetalleAnuncioScreen(
                anuncioId = anuncioId,
                navController = navController
            )
        }


        /* ---------------- PUBLICAR ---------------- */

        composable(Routes.Publicar.route) {
            navController.navigate(Routes.PublicarPaso1.route) {
                launchSingleTop = true
            }
        }

        composable(Routes.PublicarPaso1.route) {
            Paso1Screen(
                viewModel = publicarViewModel,
                onContinue = {
                    publicarViewModel.cargarFormulario()
                    navController.navigate(Routes.PublicarPaso2.route)
                }
            )
        }

        composable(Routes.PublicarPaso2.route) {
            Paso2Screen(
                viewModel = publicarViewModel,
                onPublicar = {
                    navController.navigate(Routes.PublicarResumen.route)
                }
            )
        }

        composable(Routes.PublicarResumen.route) {
            ResumenPublicarScreen(
                viewModel = publicarViewModel,
                navController = navController,
                onFinish = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- TABS ---------------- */

        composable(Routes.Favoritos.route) {
            FavoritosScreen(navController = navController)
        }
        composable(Routes.Mensajes.route) { Text("Mensajes") }

        /* ---------------- PERFIL  ---------------- */


        composable(Routes.Perfil.route) {
            PerfilScreen(navController)
        }

        composable(Routes.MisAnuncios.route) {
            MisAnunciosScreen(navController)
        }

        composable(
            route = Routes.EditarAnuncio.route,
            arguments = listOf(navArgument("anuncioId") { type = NavType.StringType })
        ) { backStackEntry ->
            val anuncioId = backStackEntry.arguments?.getString("anuncioId")!!
            EditarAnuncioScreen(anuncioId, navController)
        }

        composable(Routes.Saldo.route) {
            SaldoScreen(navController)
        }

        composable(Routes.DatosPersonales.route) {
            DatosPersonalesScreen(navController)
        }

        composable(Routes.DatosPersonales.route + "/editar") {
            EditarDatosPersonalesScreen(navController)
        }

        composable(Routes.CambiarPassword.route) {
            CambiarPasswordScreen(navController)
        }

        composable(Routes.ChatMirly.route) {
            ChatMirlyScreen(navController)
        }

        composable(Routes.SobreMirly.route) {
            SobreMirlyScreen(navController)
        }

        composable(Routes.Ayuda.route) {
            AyudaScreen(navController)
        }

        composable(Routes.Contacto.route) {
            ContactoScreen(navController)
        }

    }
}
