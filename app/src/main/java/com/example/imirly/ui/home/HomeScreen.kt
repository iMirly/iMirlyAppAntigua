package com.example.imirly.ui.home

import HomeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imirly.ui.components.CategoryCard
import com.example.imirly.ui.home.components.CategoriesGrid
import com.example.imirly.ui.home.components.HomeBanner
import com.example.imirly.ui.home.components.HomeHeader
import com.example.imirly.ui.home.components.HomeSearchBar
import com.example.imirly.ui.home.components.SectionTitle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onCategoryClick: (String) -> Unit
) {
    val categorias = viewModel.categorias.value
    val searchQuery = viewModel.searchQuery.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        HomeHeader()

        Spacer(Modifier.height(12.dp))

        HomeSearchBar(
            value = searchQuery,
            onValueChange = { viewModel.onSearchChange(it) },
            placeholder = "Buscar servicios..."
        )

        Spacer(Modifier.height(16.dp))

        HomeBanner(
            title = "¿Qué servicio necesitas?",
            subtitle = "Profesionales verificados y con experiencia"
        )

        Spacer(Modifier.height(20.dp))

        SectionTitle(title = "Servicios")

        Spacer(Modifier.height(12.dp))

        CategoriesGrid(
            categorias = categorias,
            onCategoryClick = onCategoryClick
        )
    }
}
