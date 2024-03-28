package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import com.ridhaaf.techtopia.feature.data.Product

@Composable
fun ProductItem(product: Product) {
    Card {
        ProductImage()
        ProductDetail(
            name = product.name,
            rating = product.rating,
            sold = product.sold,
            price = product.price,
        )
    }
}