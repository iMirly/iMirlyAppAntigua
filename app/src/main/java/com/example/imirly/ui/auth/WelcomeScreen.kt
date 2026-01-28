package com.example.imirly.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onSocialLoginSuccess: () -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Placeholder
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(Color(0xFFE0E0E0), CircleShape)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Todos los servicios\nal alcance de tu mano",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF4A34AC),
                    fontWeight = FontWeight.Normal,
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = { showSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7E69D4)
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4329AC)
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                AuthOptionsSheetContent(
                    onClose = { showSheet = false },
                    onNavigateToEmailRegister = {
                        showSheet = false
                        onNavigateToRegister()
                    },
                    onNavigateToLogin = {
                        showSheet = false
                        onNavigateToLogin()
                    },
                    onAppleLogin = {
                        showSheet = false
                        onSocialLoginSuccess()
                    }
                )
            }
        }
    }
}

@Composable
fun AuthOptionsSheetContent(
    onClose: () -> Unit,
    onNavigateToEmailRegister: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onAppleLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Acceder a iMirly",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF4A34AC),
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(
                onClick = onClose,
                modifier = Modifier.background(Color(0xFFE0E0E0), CircleShape).size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Social Buttons
        SocialAuthButton(
            text = "Continuar con apple",
            containerColor = Color.Black,
            contentColor = Color.White,
            onClick = onAppleLogin
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialAuthButton(
            text = "Continuar con facebook",
            containerColor = Color(0xFF1877F2),
            contentColor = Color.White,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialAuthButton(
            text = "Continuar con google",
            containerColor = Color.White,
            contentColor = Color.Black,
            border = BorderStroke(1.dp, Color.LightGray),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Separator
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
            Text(
                text = "o",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black,
                fontSize = 14.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Email Register
        OutlinedButton(
            onClick = onNavigateToEmailRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(
                text = "Regístrate con tu correo electrónico",
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer Link
        val annotatedString = buildAnnotatedString {
            append("¿Tienes una cuenta? ")
            withStyle(style = SpanStyle(color = Color(0xFF7E69D4), fontWeight = FontWeight.Bold)) {
                append("Inicia sesión")
            }
        }
        
        TextButton(onClick = onNavigateToLogin) {
            Text(text = annotatedString, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Legal Text
        Text(
            text = buildAnnotatedString {
                append("Al crear una cuenta, acepto los ")
                withStyle(style = SpanStyle(color = Color(0xFF7E69D4))) { append("Términos y condiciones") }
                append(" y confirmo que he leído la ")
                withStyle(style = SpanStyle(color = Color(0xFF7E69D4))) { append("Política de privacidad") }
            },
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.Black,
            lineHeight = 16.sp
        )
    }
}

@Composable
fun SocialAuthButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    border: BorderStroke? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
        border = border,
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Medium)
    }
}
