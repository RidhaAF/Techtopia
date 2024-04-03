package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductImage(product: Product) {
    val image = product.imagesUrl.firstOrNull()
    val name = product.name

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .aspectRatio(1f)
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        AsyncImage(model = image, contentDescription = name)
    }
}