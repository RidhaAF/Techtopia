package com.ridhaaf.techtopia.feature.domain.repositories.product

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.product.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<Resource<List<Product>>>
    fun getProductById(id: String): Flow<Resource<Product>>
    fun getBestSellingProducts(): Flow<Resource<List<Product>>>
    fun getProductsByCategory(categoryId: String): Flow<Resource<List<Product>>>
    fun searchProducts(query: String): Flow<Resource<List<Product>>>
}