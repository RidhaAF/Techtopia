package com.ridhaaf.techtopia.feature.domain.repositories.cart

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.cart.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun addProductToCart(productId: String): Flow<Resource<Unit>>
    fun removeProductFromCart(productId: String): Flow<Resource<Unit>>
    fun getCart(): Flow<Resource<Cart>>
    fun addProductQuantity(productId: String): Flow<Resource<Unit>>
    fun reduceProductQuantity(productId: String): Flow<Resource<Unit>>
}