package com.ridhaaf.techtopia.feature.domain.repositories.wishlist

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    fun getWishlistByUserId(): Flow<Resource<List<Wishlist>>>
    fun addWishlist(productId: String): Flow<Resource<Unit>>
    fun removeWishlist(id: String): Flow<Resource<Unit>>
}