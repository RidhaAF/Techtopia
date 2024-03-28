package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.category.Category

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Text(text = category.name)
    }
}