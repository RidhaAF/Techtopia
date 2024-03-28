package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProductName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.bodyMedium,
    )
}