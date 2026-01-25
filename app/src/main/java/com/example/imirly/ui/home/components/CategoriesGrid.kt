package com.example.imirly.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imirly.data.model.Categoria
import com.example.imirly.ui.components.CategoryCard


@Composable
fun CategoriesGrid(
    categorias: List<Categoria>,
    onCategoryClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categorias) { categoria ->
            CategoryCard(
                categoria = categoria,
                onClick = {
                    onCategoryClick(categoria.id)
                }
            )
        }
    }
}
