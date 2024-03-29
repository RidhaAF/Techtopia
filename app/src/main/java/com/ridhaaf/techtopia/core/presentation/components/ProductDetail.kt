package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductDetail(
    name: String,
    rating: Double,
    sold: Int,
    price: Double,
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        ProductName(name = name)
        ProductRating(rating = rating, sold = sold)
        ProductPrice(price = price)
    }
}