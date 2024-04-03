package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductItem(product: Product) {
    Card {
        ProductImage(product)
        ProductDetail(product)
    }
}