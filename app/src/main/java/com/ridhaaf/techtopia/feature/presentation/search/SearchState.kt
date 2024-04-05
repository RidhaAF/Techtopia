package com.ridhaaf.techtopia.feature.presentation.search

import com.ridhaaf.techtopia.feature.data.models.product.Product

data class SearchState(
    val isSearchLoading: Boolean = false,
    val searchSuccess: List<Product>? = null,
    val searchError: String = "",
)
