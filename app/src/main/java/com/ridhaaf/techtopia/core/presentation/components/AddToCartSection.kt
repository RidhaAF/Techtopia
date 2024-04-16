package com.ridhaaf.techtopia.core.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailState
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailViewModel

@Composable
fun AddToCartSection(
    viewModel: ProductDetailViewModel,
    state: ProductDetailState,
    context: Context,
    product: Product,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        contentAlignment = Alignment.Center,
    ) {
        val loading = state.isCartLoading
        val success = state.cartSuccess
        val error = state.cartError

        AddToCartButton(
            viewModel = viewModel,
            product = product,
            loading = loading,
        )

        LaunchedEffect(key1 = state) {
            if (success != null) {
                defaultToast(context, "Successfully added to cart")
            } else if (error.isNotBlank()) {
                defaultToast(context, error)
            }
        }
    }
}