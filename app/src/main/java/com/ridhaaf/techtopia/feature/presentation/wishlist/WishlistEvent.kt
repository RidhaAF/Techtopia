package com.ridhaaf.techtopia.feature.presentation.wishlist

sealed class WishlistEvent {
    data object Refresh : WishlistEvent()
    data class Remove(val id: String) : WishlistEvent()
}