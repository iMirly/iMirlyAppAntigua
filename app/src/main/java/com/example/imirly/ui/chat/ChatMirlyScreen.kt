package com.example.imirly.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun ChatMirlyScreen(
    navController: NavHostController,
    viewModel: ChatMirlyViewModel = viewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {

        /* ---------- HEADER ---------- */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Chat de Mirly",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Divider()

        /* ---------- MENSAJES ---------- */
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.messages) { message ->
                ChatBubble(message)

                message.quickReplies?.let { replies ->
                    QuickReplies(
                        replies = replies,
                        onReplyClick = { viewModel.onQuickReplySelected(it) }
                    )
                }
            }
        }

        /* ---------- INPUT (desactivado) ---------- */
        ChatInput()
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else Color(0xFFF2F2F2),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(text = message.text)
        }
    }
}

@Composable
fun QuickReplies(
    replies: List<QuickReply>,
    onReplyClick: (QuickReply) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 8.dp)
    ) {
        replies.forEach { reply ->
            OutlinedButton(
                onClick = { onReplyClick(reply) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(reply.text)
            }
        }
    }
}

@Composable
fun ChatInput() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Escribe tu mensaje…") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp),
            enabled = false
        )

        Spacer(Modifier.width(8.dp))

        IconButton(onClick = {}, enabled = false) {
            Icon(Icons.Default.Send, contentDescription = null)
        }
    }
}
