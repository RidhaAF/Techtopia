package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductDetailSection(product: Product) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
            .padding(bottom = 64.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ProductDetailImageContent(product)
        ProductDetailContentSection(product)
    }
}