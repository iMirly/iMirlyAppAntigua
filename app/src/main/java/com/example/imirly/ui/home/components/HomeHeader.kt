package com.example.imirly.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeHeader() {
    Column {
        Text(
            text = "iMirly",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Encuentra tu profesional ideal",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
