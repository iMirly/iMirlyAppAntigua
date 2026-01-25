package com.example.imirly.ui.subcategories
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.imirly.ui.components.SubcategoryCard
import com.example.imirly.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubcategoriesScreen(
    categoryId: String,
    navController: NavController
) {
    val context = LocalContext.current
    val factory = SubcategoriesViewModelFactory(
        context.applicationContext as android.app.Application,
        categoryId
    )
    val viewModel: SubcategoriesViewModel = viewModel(factory = factory)

    val subcategorias = viewModel.subcategorias.value

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔙 TOP BAR CON BOTÓN ATRÁS
        TopAppBar(
            title = {
                Text("Subcategorías")
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        )

        // CONTENIDO
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(subcategorias.size) { index ->
                SubcategoryCard(
                    subcategoria = subcategorias[index],
                    onClick = {
                        navController.navigate(
                            Routes.Anuncios.createRoute(
                                categoryId,
                                subcategorias[index].id
                            )
                        )
                    }
                )
            }
        }
    }
}
