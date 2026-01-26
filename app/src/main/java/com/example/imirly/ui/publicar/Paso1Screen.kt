package com.example.imirly.ui.publicar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.Categoria
import com.example.imirly.data.model.Subcategoria

/* ================= PANTALLA PASO 1 ================= */

@Composable
fun Paso1Screen(
    viewModel: PublicarViewModel,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        /*
        PasoHeader(
            titulo = "Nuevo anuncio",
            pasoActual = 1,
            totalPasos = 2
        )
         */
        PasoHeaderPaso1(
            onBack = onBack
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                CardBase {
                    Text("Nombre")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.nombre.value,
                        onValueChange = { viewModel.nombre.value = it },
                        placeholder = { Text("Introduce tu nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        )
                    )
                }
            }

            item {
                CardBase {
                    Text("Apellidos")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.apellidos.value,
                        onValueChange = { viewModel.apellidos.value = it },
                        placeholder = { Text("Introduce tus apellidos") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        )
                    )
                }
            }

            item {
                CardBase {
                    Text("DNI")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.dni.value,
                        onValueChange = { viewModel.dni.value = it },
                        placeholder = { Text("12345678A") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters
                        )
                    )
                }
            }

            item {
                CardBase {
                    Text("Número de teléfono")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.telefono.value,
                        onValueChange = { viewModel.telefono.value = it },
                        placeholder = { Text("+34 600 000 000") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        )
                    )
                }
            }

            item {
                FotoServicioCard()
            }

            item {
                SelectorCategoria(
                    categorias = viewModel.categorias.value,
                    selectedId = viewModel.categoriaId.value,
                    onSelect = { viewModel.seleccionarCategoria(it.id) }
                )
            }

            item {
                SelectorSubcategoria(
                    subcategorias = viewModel.subcategorias.value,
                    enabled = viewModel.categoriaId.value.isNotBlank(),
                    selectedId = viewModel.subcategoriaId.value,
                    onSelect = { viewModel.seleccionarSubcategoria(it.id) }
                )
            }


            item {
                CardBase {
                    Text("Título del servicio")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.titulo.value,
                        onValueChange = { viewModel.titulo.value = it },
                        placeholder = {
                            Text("Ej: Fontanero profesional con experiencia")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )
                }
            }

            /*
            item {
                CardBase {
                    Text("Precio por hora")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.precioHora.value,
                        onValueChange = { viewModel.precioHora.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        trailingIcon = { Text("€/h") }
                    )
                }
            }
            */

            item {
                CardBase {
                    Text("Ubicación")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.ubicacion.value,
                        onValueChange = { viewModel.ubicacion.value = it },
                        placeholder = { Text("Ej: Madrid Centro") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                CardBase {
                    Text("Descripción")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.descripcion.value,
                        onValueChange = {
                            if (it.length <= 500) viewModel.descripcion.value = it
                        },
                        placeholder = {
                            Text("Describe tu servicio, experiencia y lo que ofreces")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("${viewModel.descripcion.value.length}/500")
                }
            }
        }

        Button(
            onClick = {
                if (viewModel.validarPaso1()) {
                    viewModel.cargarFormulario()
                    onContinue()
                }
            },
            enabled = viewModel.validarPaso1(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp)
        ) {
            Text("Continuar")
        }

    }
}

/* ================= COMPONENTES ================= */

@Composable
fun PasoHeader(
    titulo: String,
    pasoActual: Int,
    totalPasos: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(titulo, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(progress = pasoActual / totalPasos.toFloat())
        Spacer(Modifier.height(4.dp))
        Text("Paso $pasoActual de $totalPasos")
    }
}

@Composable
fun CardBase(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun FotoServicioCard() {
    CardBase {
        Text("Foto del servicio")
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Subir imagen\nJPG, PNG o GIF (máx. 5MB)")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorCategoria(
    categorias: List<Categoria>,
    selectedId: String,
    onSelect: (Categoria) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedNombre =
        categorias.firstOrNull { it.id == selectedId }?.nombre ?: ""

    CardBase {
        Text("Categoría")
        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedNombre,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Selecciona una categoría") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nombre) },
                        onClick = {
                            expanded = false
                            onSelect(categoria)
                        }
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorSubcategoria(
    subcategorias: List<Subcategoria>,
    enabled: Boolean,
    selectedId: String,
    onSelect: (Subcategoria) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedNombre =
        subcategorias.firstOrNull { it.id == selectedId }?.nombre ?: ""

    CardBase {
        Text("Subcategoría")
        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedNombre,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                placeholder = {
                    Text(
                        if (enabled)
                            "Selecciona una subcategoría"
                        else
                            "Selecciona una categoría primero"
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                subcategorias.forEach { sub ->
                    DropdownMenuItem(
                        text = { Text(sub.nombre) },
                        onClick = {
                            expanded = false
                            onSelect(sub)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PasoHeaderPaso1(
    onBack: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text("Nuevo anuncio", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(progress = 0.5f)
        Spacer(Modifier.height(4.dp))
        Text("Paso 1 de 2")
    }
}

