package com.example.imirly.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterStepNameScreen(
    viewModel: RegisterViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                LinearProgressIndicator(
                    progress = { 0.33f },
                    modifier = Modifier.fillMaxWidth().height(4.dp),
                    color = Color(0xFF4A34AC),
                    trackColor = Color(0xFFE0E0E0)
                )
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color(0xFF4A34AC)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Cómo te llamas?",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF4A34AC),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Introduce tu nombre y apellidos para finalizar correctamente la creación de tu cuenta:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nombre Field
            CustomRegisterTextField(
                value = viewModel.firstName,
                onValueChange = { if (it.length <= 50) viewModel.firstName = it },
                placeholder = "Nombre",
                counter = "${viewModel.firstName.length}/50"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Apellidos Field
            CustomRegisterTextField(
                value = viewModel.lastName,
                onValueChange = { if (it.length <= 50) viewModel.lastName = it },
                placeholder = "Apellidos",
                counter = "${viewModel.lastName.length}/50"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7E69D4),
                    disabledContainerColor = Color(0xFF7E69D4).copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = viewModel.firstName.isNotBlank() && viewModel.lastName.isNotBlank()
            ) {
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun CustomRegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    counter: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray.copy(alpha = 0.7f)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFD7D1F3),
                unfocusedContainerColor = Color(0xFFD7D1F3),
                disabledContainerColor = Color(0xFFD7D1F3),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF4A34AC)
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        Text(
            text = counter,
            modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}
