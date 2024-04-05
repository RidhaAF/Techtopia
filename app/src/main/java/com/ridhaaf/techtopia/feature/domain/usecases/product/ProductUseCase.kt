package com.ridhaaf.techtopia.feature.domain.usecases.product

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.cart.Cart
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.domain.repositories.product.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductUseCase(private val repository: ProductRepository) {
    fun getProducts(): Flow<Resource<List<Product>>> {
        return repository.getProducts()
    }

    fun getProductById(id: String): Flow<Resource<Product>> {
        return repository.getProductById(id)
    }

    fun getBestSellingProducts(): Flow<Resource<List<Product>>> {
        return repository.getBestSellingProducts()
    }

    fun getProductsByCategory(categoryId: String): Flow<Resource<List<Product>>> {
        return repository.getProductsByCategory(categoryId)
    }

    fun searchProducts(query: String): Flow<Resource<List<Product>>> {
        return repository.searchProducts(query)
    }

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