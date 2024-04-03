package com.ridhaaf.techtopia.feature.domain.usecases.product

import com.ridhaaf.techtopia.core.utils.Resource
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

    fun searchProducts(query: String): Flow<Resource<List<Product>>> {
        return repository.searchProducts(query)
    }
}