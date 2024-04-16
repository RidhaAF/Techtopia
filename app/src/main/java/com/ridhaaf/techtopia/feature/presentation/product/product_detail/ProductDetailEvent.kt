package com.ridhaaf.techtopia.feature.presentation.product.product_detail

sealed class ProductDetailEvent {
    data class Refresh(val id: String) : ProductDetailEvent()
    data class AddToCart(val productId: String) : ProductDetailEvent()
    data class AddToWishlist(val productId: String) : ProductDetailEvent()
    data class RemoveFromWishlist(
        val productId: String,
        val wishlistId: String,
    ) : ProductDetailEvent()
}