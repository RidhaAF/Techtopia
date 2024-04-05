package com.ridhaaf.techtopia.feature.presentation.product

sealed class ProductEvent {
    data class Refresh(
        val type: String,
        val categoryId: String,
    ) : ProductEvent()
}