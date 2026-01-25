package com.example.imirly.ui.publicar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.example.imirly.data.model.Categoria
import com.example.imirly.data.model.Subcategoria

@Composable
fun Paso1Screen(
    viewModel: PublicarViewModel,
    onContinue: () -> Unit
) {
    val categorias = viewModel.categorias.value
    val subcategorias = viewModel.subcategorias.value



    val provinciasEspaña = listOf(
        "Álava","Albacete","Alicante","Almería","Asturias","Ávila",
        "Badajoz","Barcelona","Burgos","Cáceres","Cádiz","Cantabria",
        "Castellón","Ciudad Real","Córdoba","Cuenca",
        "Girona","Granada","Guadalajara","Guipúzcoa","Huelva","Huesca",
        "Illes Balears","Jaén","La Coruña","La Rioja","Las Palmas","León",
        "Lleida","Lugo","Madrid","Málaga","Murcia","Navarra","Ourense",
        "Palencia","Pontevedra","Salamanca","Santa Cruz de Tenerife",
        "Segovia","Sevilla","Soria","Tarragona","Teruel","Toledo",
        "Valencia","Valladolid","Vizcaya","Zamora","Zaragoza"
    )

    Column(modifier = Modifier.fillMaxSize()) {

        PasoHeader(
            titulo = "Nuevo anuncio",
            pasoActual = 1,
            totalPasos = 2
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { FotoServicioCard() }

            item {
                SelectorCategoriaCard(
                    categorias = categorias,
                    selectedId = viewModel.categoriaId.value,
                    onSelect = { categoria ->
                        viewModel.seleccionarCategoria(categoria.id)
                    }
                )
            }

            item {
                SelectorSubcategoriaCard(
                    subcategorias = subcategorias,
                    enabled = viewModel.categoriaId.value.isNotBlank(),
                    selectedId = viewModel.subcategoriaId.value,
                    onSelect = { sub ->
                        viewModel.seleccionarSubcategoria(sub.id)
                    }
                )
            }

            /* ---------- TÍTULO ---------- */

            item {
                CardBase {
                    Text("Título del servicio", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = viewModel.titulo.value,
                        onValueChange = { viewModel.titulo.value = it },
                        placeholder = {
                            Text("Ej: Cuelgo cuadros y estanterías")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            /* ---------- DESCRIPCIÓN ---------- */

            item {
                CardBase {
                    Text("Descripción del servicio", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = viewModel.descripcion.value,
                        onValueChange = { viewModel.descripcion.value = it },
                        placeholder = {
                            Text("Describe tu experiencia, qué incluye el servicio, materiales, etc.")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            /* ---------- UBICACIÓN ---------- */

            item {
                SelectorDropdownCard(
                    label = "Ubicación",
                    enabled = true,
                    selectedText = viewModel.provincia.value,
                    placeholderText = "Selecciona una provincia",
                    items = provinciasEspaña,
                    onItemSelected = { provincia ->
                        viewModel.provincia.value = provincia
                    }
                )
            }
        }

        /* ---------- CONTINUAR ---------- */

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
        Modifier
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
fun CardBase(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp), content = content)
    }
}

@Composable
fun FotoServicioCard() {
    CardBase {
        Text("Foto del servicio", style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Subir imagen")
        }
    }
}

@Composable
fun SelectorCategoriaCard(
    categorias: List<Categoria>,
    selectedId: String,
    onSelect: (Categoria) -> Unit
) {
    val selectedNombre = categorias.firstOrNull { it.id == selectedId }?.nombre ?: ""

    SelectorDropdownCard(
        label = "Categoría",
        enabled = true,
        selectedText = selectedNombre,
        placeholderText = "Selecciona",
        items = categorias.map { it.nombre },
        onItemSelected = { nombre ->
            categorias.firstOrNull { it.nombre == nombre }?.let(onSelect)
        }
    )
}

@Composable
fun SelectorSubcategoriaCard(
    subcategorias: List<Subcategoria>,
    enabled: Boolean,
    selectedId: String,
    onSelect: (Subcategoria) -> Unit
) {
    val selectedNombre = subcategorias.firstOrNull { it.id == selectedId }?.nombre ?: ""

    SelectorDropdownCard(
        label = "Subcategoría",
        enabled = enabled,
        selectedText = selectedNombre,
        placeholderText = if (enabled) "Selecciona" else "Selecciona una categoría primero",
        items = subcategorias.map { it.nombre },
        onItemSelected = { nombre ->
            subcategorias.firstOrNull { it.nombre == nombre }?.let(onSelect)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorDropdownCard(
    label: String,
    enabled: Boolean,
    selectedText: String,
    placeholderText: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    CardBase {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                placeholder = { Text(placeholderText) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            expanded = false
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}
