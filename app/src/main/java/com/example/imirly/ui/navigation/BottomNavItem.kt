package com.example.imirly.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.ai.client.generativeai.Chat

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Routes.Home.route,
        label = "Inicio",
        icon = Icons.Outlined.Home
    )

    object Favoritos : BottomNavItem(
        route = "favoritos",
        label = "Favoritos",
        icon = Icons.Outlined.FavoriteBorder
    )

    object Publicar : BottomNavItem(
        route = "publicar",
        label = "Publicar",
        icon = Icons.Outlined.AddCircle
    )

    object Mensajes : BottomNavItem(
        route = "mensajes",
        label = "Mensajes",
        icon = Icons.Outlined.Person
    )

    object Perfil : BottomNavItem(
        route = "perfil",
        label = "Perfil",
        icon = Icons.Outlined.Person
    )
}
