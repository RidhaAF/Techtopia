package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductItem(product: Product) {
    Card {
        Box(modifier = Modifier.aspectRatio(1f)) {
            ProductImage(product)
        }
        ProductDetail(product)
    }
}