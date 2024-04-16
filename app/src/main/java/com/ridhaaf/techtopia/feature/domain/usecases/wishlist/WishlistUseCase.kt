package com.ridhaaf.techtopia.feature.domain.usecases.wishlist

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import com.ridhaaf.techtopia.feature.domain.repositories.wishlist.WishlistRepository
import kotlinx.coroutines.flow.Flow

class WishlistUseCase(private val repository: WishlistRepository) {
    fun getWishlistsByUserId(): Flow<Resource<List<Wishlist>>> {
        return repository.getWishlistsByUserId()
    }

    fun addWishlist(productId: String): Flow<Resource<Unit>> {
        return repository.addWishlist(productId)
    }

    fun removeWishlist(id: String): Flow<Resource<Unit>> {
        return repository.removeWishlist(id)
    }
}