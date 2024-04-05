package com.ridhaaf.techtopia.feature.presentation.product.product_detail

sealed class ProductDetailEvent {
    data class Refresh(val id: String) : ProductDetailEvent()
}