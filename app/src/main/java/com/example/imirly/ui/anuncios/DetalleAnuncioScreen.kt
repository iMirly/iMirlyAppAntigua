package com.example.imirly.ui.anuncios

import android.app.Application
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imirly.data.model.Anuncio
import com.example.imirly.data.model.Formulario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleAnuncioScreen(
    anuncioId: String,
    navController: NavHostController
) {
    val vm: DetalleAnuncioViewModel = viewModel(
        factory = DetalleAnuncioViewModelFactory(
            navController.context.applicationContext as Application,
            anuncioId
        )
    )

    val anuncio = vm.anuncio
    val formulario = vm.formulario

    if (anuncio == null || formulario == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    DetalleAnuncioContent(
        anuncio = anuncio,
        formulario = formulario,
        navController = navController
    )
}

@Composable
fun PerfilCard(anuncio: Anuncio) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp)
                )

                IconButton(
                    onClick = { /* favorito */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(32.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                }
            }

            Spacer(Modifier.height(8.dp))

            Text("Isabel", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(4.dp))

            Text("⭐ 4.8 · 127 reseñas")
        }
    }
}
@Composable
fun PrecioCard(anuncio: Anuncio) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "13,50€",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                "por hora",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
@Composable
fun SobreMiCard(descripcion: String) {
    Card {
        Column(Modifier.padding(16.dp)) {
            Text("Sobre mí", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "Soy una profesional de ayuda a las familias que sea dificulta conciliar..."
            )
        }
    }
}
@Composable
fun ContactarButton() {
    Button(
        onClick = { /* ir a chat */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text("Contactar")
    }
}




@Composable
fun DetallesServicioCard(
    anuncio: Anuncio,
    formulario: Formulario
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Detalles del servicio",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            formulario.campos.forEach { campo ->
                val valor = anuncio.detalles.opt(campo.id) ?: return@forEach

                when (campo.tipo) {

                    "boolean" -> {
                        DetalleBooleanRow(
                            label = campo.label,
                            value = valor as Boolean
                        )
                    }

                    "number" -> {
                        DetalleTextoRow(
                            label = campo.label,
                            value = valor.toString()
                        )
                    }

                    "text" -> {
                        DetalleTextoRow(
                            label = campo.label,
                            value = valor.toString()
                        )
                    }

                    "select" -> {
                        if (valor is List<*>) {
                            DetalleChipsRow(
                                label = campo.label,
                                valores = valor.filterIsInstance<String>()
                            )
                        } else {
                            DetalleTextoRow(
                                label = campo.label,
                                value = valor.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetalleBooleanRow(
    label: String,
    value: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (value) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = if (value) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.error
        )
    }
}


@Composable
fun DetalleTextoRow(
    label: String,
    value: String
) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value)
    }
}

@Composable
fun DetalleChipsRow(
    label: String,
    valores: List<String>
) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(6.dp))

        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            valores.forEach {
                AssistChip(
                    onClick = {},
                    label = { Text(it) }
                )
            }
        }
    }
}
