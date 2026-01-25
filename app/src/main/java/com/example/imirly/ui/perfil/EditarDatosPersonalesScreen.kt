package com.example.imirly.ui.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imirly.data.local.UserData
import com.example.imirly.data.local.UserStore
import com.example.imirly.data.model.UserProfile

@Composable
fun EditarDatosPersonalesScreen(
    navController: NavHostController,
    viewModel: DatosPersonalesViewModel = viewModel()
) {
    val profile by viewModel.profile.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    // 🔥 SINCRONIZA CUANDO LLEGA EL PERFIL
    LaunchedEffect(profile) {
        name = profile.name
        email = profile.email
        phone = profile.phone
        birthdate = profile.birthdate
        address = profile.address
        city = profile.city
        postalCode = profile.postalCode
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = "Editar datos personales",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = birthdate,
                onValueChange = { birthdate = it },
                label = { Text("Fecha de nacimiento") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = postalCode,
                onValueChange = { postalCode = it },
                label = { Text("Código postal") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.saveProfile(
                        UserProfile(
                            name = name,
                            email = email,
                            phone = phone,
                            birthdate = birthdate,
                            address = address,
                            city = city,
                            postalCode = postalCode
                        )
                    ) {
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
