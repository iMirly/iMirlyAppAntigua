package com.example.imirly.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/* ---------------- MODELO ---------------- */

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val quickReplies: List<QuickReply>? = null
)

data class QuickReply(
    val id: String,
    val text: String
)

/* ---------------- VIEWMODEL ---------------- */

class ChatMirlyViewModel : ViewModel() {

    val messages = mutableStateListOf<ChatMessage>()

    init {
        showWelcome()
    }

    /* ---------------- MENSAJE INICIAL ---------------- */

    private fun showWelcome() {
        messages.add(
            ChatMessage(
                text = "¡Hola! 👋 Soy el asistente de Mirly.\n¿En qué puedo ayudarte?",
                isUser = false,
                quickReplies = mainOptions()
            )
        )
    }

    /* ---------------- OPCIONES PRINCIPALES ---------------- */

    private fun mainOptions() = listOf(
        QuickReply("publicar", "¿Cómo publico un servicio?"),
        QuickReply("contactar", "¿Cómo contacto con un profesional?"),
        QuickReply("pago", "Problemas con un pago"),
        QuickReply("problema", "Reportar un problema")
    )

    /* ---------------- CLICK EN RESPUESTA RÁPIDA ---------------- */

    fun onQuickReplySelected(reply: QuickReply) {
        addUserMessage(reply.text)

        when (reply.id) {
            "publicar" -> answerPublicar()
            "contactar" -> answerContactar()
            "pago" -> answerPago()
            "problema" -> answerProblema()
            "volver" -> showWelcome()
        }
    }

    /* ---------------- TEXTO ESCRITO POR USUARIO ---------------- */

    fun onUserTextMessage(text: String) {
        addUserMessage(text)

        val lower = text.lowercase()

        when {
            lower.contains("publicar") -> answerPublicar()
            lower.contains("contactar") || lower.contains("hablar") -> answerContactar()
            lower.contains("pago") || lower.contains("cobro") -> answerPago()
            lower.contains("problema") || lower.contains("error") -> answerProblema()
            else -> answerUnknown()
        }
    }

    /* ---------------- RESPUESTAS ---------------- */

    private fun answerPublicar() {
        addBotMessage(
            text =
                "Publicar un servicio es muy fácil 😊\n\n" +
                        "1️⃣ Pulsa el botón ➕ Publicar\n" +
                        "2️⃣ Elige la categoría\n" +
                        "3️⃣ Rellena los datos\n\n" +
                        "Tu anuncio se publicará al instante.",
            quickReplies = backOption()
        )
    }

    private fun answerContactar() {
        addBotMessage(
            text =
                "Puedes contactar con un profesional desde su anuncio 💬\n\n" +
                        "👉 Entra en el anuncio\n" +
                        "👉 Pulsa en “Contactar”\n" +
                        "👉 Chatea directamente con él",
            quickReplies = backOption()
        )
    }

    private fun answerPago() {
        addBotMessage(
            text =
                "Si tienes problemas con un pago 💳:\n\n" +
                        "• Revisa tu método de pago\n" +
                        "• Comprueba tu saldo\n\n" +
                        "Si el problema continúa, nuestro equipo te ayudará.",
            quickReplies = backOption()
        )
    }

    private fun answerProblema() {
        addBotMessage(
            text =
                "Gracias por avisarnos 🛠️\n\n" +
                        "Describe el problema y lo revisaremos lo antes posible.",
            quickReplies = backOption()
        )
    }

    private fun answerUnknown() {
        addBotMessage(
            text =
                "No estoy seguro de haber entendido 🤔\n" +
                        "Elige una opción o reformula tu pregunta.",
            quickReplies = mainOptions()
        )
    }

    /* ---------------- HELPERS ---------------- */

    private fun backOption() = listOf(
        QuickReply("volver", "Volver al inicio")
    )

    private fun addUserMessage(text: String) {
        messages.add(ChatMessage(text = text, isUser = true))
    }

    private fun addBotMessage(
        text: String,
        quickReplies: List<QuickReply>? = null
    ) {
        messages.add(
            ChatMessage(
                text = text,
                isUser = false,
                quickReplies = quickReplies
            )
        )
    }
}
