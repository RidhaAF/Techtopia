package com.ridhaaf.techtopia.feature.presentation.product.product_detail

import com.ridhaaf.techtopia.feature.data.models.product.Product

data class ProductDetailState(
    val isProductLoading: Boolean = false,
    val productSuccess: Product? = null,
    val productError: String = "",
)
