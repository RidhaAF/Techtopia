package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailEvent
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailViewModel

@Composable
fun AddToCartButton(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel,
    product: Product,
    loading: Boolean,
) {
    DefaultButton(
        modifier = modifier.padding(horizontal = 16.dp),
        onClick = {
            val id = product.id
            viewModel.onEvent(ProductDetailEvent.AddToCart(id))
        },
    ) {
        val text = if (loading) "Adding to cart..." else "+ Cart"

        Text(
            text,
            fontWeight = FontWeight.Bold,
        )
    }
}