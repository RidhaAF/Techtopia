package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.category.Category

@Composable
fun CategoriesSection() {
    val categories = listOf(
        Category(1, "Phones"),
        Category(2, "Laptops"),
        Category(3, "Tablets"),
        Category(4, "Accessories"),
        Category(5, "Smart Home"),
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                onClick = { /*TODO*/ },
            )
        }
    }
}