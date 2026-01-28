package com.example.imirly.ui.navigation

sealed class Routes(val route: String) {

    /* ---------------- START / AUTH FLOW ---------------- */

    object Start : Routes("start")
    object Onboarding : Routes("onboarding")
    object Welcome : Routes("welcome")
    object Login : Routes("login")
    
    // Flujo de Registro Paso a Paso
    object RegisterStepName : Routes("register/name")
    object RegisterStepCity : Routes("register/city")
    object RegisterStepEmail : Routes("register/email")
    object RegisterStepPassword : Routes("register/password")

    /* ---------------- RUTAS PRINCIPALES (BOTTOM BAR) ---------------- */

    object Home : Routes("home")
    object Favoritos : Routes("favoritos")
    object Publicar : Routes("publicar")
    object Mensajes : Routes("mensajes")
    object Perfil : Routes("perfil")

    /* ---------------- FLUJO PUBLICAR ---------------- */

    object PublicarPaso1 : Routes("publicar/paso1")
    object PublicarPaso2 : Routes("publicar/paso2")
    object PublicarResumen : Routes("publicar/resumen")

    /* ---------------- SUBCATEGORÍAS ---------------- */

    object Subcategories : Routes("subcategories/{categoryId}") {
        fun createRoute(categoryId: String): String = "subcategories/$categoryId"
    }

    /* ---------------- ANUNCIOS ---------------- */

    object Anuncios : Routes("anuncios/{categoryId}/{subcategoriaId}") {
        fun createRoute(categoryId: String, subcategoriaId: String): String =
            "anuncios/$categoryId/$subcategoriaId"
    }

    object DetalleAnuncio : Routes(
        "detalle_anuncio/{anuncioId}"
    ) {
        fun createRoute(id: String) = "detalle_anuncio/$id"
    }

    /* ---------------- PERFIL (SUBPÁGINAS) ---------------- */

    object MisAnuncios : Routes("perfil/mis-anuncios")
    object Saldo : Routes("perfil/saldo")
    object DatosPersonales : Routes("perfil/datos-personales")
    object EditarAnuncio {
        const val route = "editar_anuncio/{anuncioId}"

        fun createRoute(anuncioId: String): String {
            return "editar_anuncio/$anuncioId"
        }
    }

    object EditarDatosPersonales : Routes("perfil/datos-personales/editar")
    object CambiarPassword : Routes("perfil/cambiar-password")
    object ChatMirly : Routes("perfil/chat-mirly")
    object SobreMirly : Routes("perfil/sobre-mirly")
    object Ayuda : Routes("perfil/ayuda")
    object Contacto : Routes("perfil/contacto")
}
