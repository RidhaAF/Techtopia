package com.ridhaaf.techtopia.feature.presentation.wishlist

import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist

data class WishlistState(
    val isWishlistLoading: Boolean = false,
    val wishlistSuccess: List<Wishlist>? = null,
    val wishlistError: String = "",
    val isRemoveLoading: Boolean = false,
    val removeSuccess: Unit? = null,
    val removeError: String = "",
)
