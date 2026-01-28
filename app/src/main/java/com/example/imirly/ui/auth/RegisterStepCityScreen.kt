package com.example.imirly.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterStepCityScreen(
    viewModel: RegisterViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val cities = listOf(
        "Alicante", "Barcelona", "Bilbao", "Córdoba", 
        "Granada", "La Coruña", "Las Palmas de Gran Canaria", "Madrid"
    )

    var selectedCity by remember { mutableStateOf(viewModel.city) }

    Scaffold(
        topBar = {
            Column {
                LinearProgressIndicator(
                    progress = { 0.40f },
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
                text = "Elige ciudad",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF4A34AC),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¿En qué ciudad quieres ofrecer tus servicios?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cities) { city ->
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { 
                                    selectedCity = city
                                    viewModel.city = city
                                }
                                .padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = city,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    color = if (selectedCity == city) Color(0xFF4A34AC) else Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            RadioButton(
                                selected = (selectedCity == city),
                                onClick = { 
                                    selectedCity = city
                                    viewModel.city = city
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF4A34AC))
                            )
                        }
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¿Aún no hemos llegado a donde vives?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))

                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF7E69D4), textDecoration = TextDecoration.Underline)) {
                        append("Solicita apertura en tu zona")
                    }
                    append(" y haremos todo lo posible para llegar cuanto antes.")
                }
                
                Text(
                    text = annotatedString,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                enabled = selectedCity.isNotBlank()
            ) {
                Text(
                    text = "Siguiente",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
