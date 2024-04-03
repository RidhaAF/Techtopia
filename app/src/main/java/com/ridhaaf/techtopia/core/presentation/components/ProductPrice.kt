package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.ridhaaf.techtopia.core.utils.currencyFormatter

@Composable
fun ProductPrice(
    price: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
    ),
) {
    Text(
        text = currencyFormatter(price),
        modifier = modifier,
        style = style,
    )
}