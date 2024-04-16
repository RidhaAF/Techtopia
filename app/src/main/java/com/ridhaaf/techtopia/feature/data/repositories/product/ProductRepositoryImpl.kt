package com.ridhaaf.techtopia.feature.data.repositories.product

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.domain.repositories.product.ProductRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : ProductRepository {
    override fun getProducts(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            val products = fetchProductsFromApi().select().decodeList<Product>()

            if (products.isEmpty()) {
                emit(Resource.Error("No products found"))
            } else {
                emit(Resource.Success(products))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getProductById(id: String): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading())

            val product = fetchProductsFromApi().select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Product>()

            emit(Resource.Success(product))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getBestSellingProducts(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            val products = fetchProductsFromApi().select {
                order("sold", Order.DESCENDING)
            }.decodeList<Product>()

            if (products.isEmpty()) {
                emit(Resource.Error("No products found"))
            } else {
                emit(Resource.Success(products))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getProductsByCategory(categoryId: String): Flow<Resource<List<Product>>> = flow {
        try {
            println("categoryId: $categoryId")
            emit(Resource.Loading())

            val products = fetchProductsFromApi().select {
                filter {
                    eq("category_id", categoryId)
                }
            }.decodeList<Product>()

            if (products.isEmpty()) {
                emit(Resource.Error("No products found"))
            } else {
                emit(Resource.Success(products))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun searchProducts(query: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            val products = fetchProductsFromApi().select {
                filter {
                    ilike("name", "%$query%")
                }
            }.decodeList<Product>()

            if (products.isEmpty()) {
                emit(Resource.Error("No products found"))
            } else {
                emit(Resource.Success(products))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private fun fetchProductsFromApi(): PostgrestQueryBuilder {
        return supabase.from("products")
    }
}