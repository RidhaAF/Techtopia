package com.ridhaaf.techtopia.feature.presentation.product

import com.ridhaaf.techtopia.feature.data.models.product.Product

data class ProductState(
    val isProductsLoading: Boolean = false,
    val productsSuccess: List<Product>? = emptyList(),
    val productsError: String = "",
)
