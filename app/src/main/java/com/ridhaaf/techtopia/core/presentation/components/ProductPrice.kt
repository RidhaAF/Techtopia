package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.ridhaaf.techtopia.core.utils.currencyFormatter

@Composable
fun ProductPrice(price: Double) {
    Text(
        text = currencyFormatter(price),
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
        ),
    )
}