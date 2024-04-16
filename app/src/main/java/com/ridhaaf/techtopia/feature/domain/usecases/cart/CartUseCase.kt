package com.ridhaaf.techtopia.feature.domain.usecases.cart

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.cart.Cart
import com.ridhaaf.techtopia.feature.domain.repositories.cart.CartRepository
import kotlinx.coroutines.flow.Flow

class CartUseCase(private val repository: CartRepository) {
    fun addProductToCart(productId: String): Flow<Resource<Unit>> {
        return repository.addProductToCart(productId)
    }

    fun removeProductFromCart(productId: String): Flow<Resource<Unit>> {
        return repository.removeProductFromCart(productId)
    }

    fun getCart(): Flow<Resource<Cart>> {
        return repository.getCart()
    }

    fun addProductQuantity(productId: String): Flow<Resource<Unit>> {
        return repository.addProductQuantity(productId)
    }

    fun reduceProductQuantity(productId: String): Flow<Resource<Unit>> {
        return repository.reduceProductQuantity(productId)
    }
}