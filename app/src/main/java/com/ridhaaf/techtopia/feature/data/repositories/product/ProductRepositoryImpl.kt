package com.ridhaaf.techtopia.feature.data.repositories.product

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import com.ridhaaf.techtopia.feature.domain.repositories.product.ProductRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
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

            val (isProductInWishlist, wishlistId) = isProductInUserWishlist(id)

            val updatedProduct =
                product.copy(isWishlist = isProductInWishlist, wishlistId = wishlistId)

            emit(Resource.Success(updatedProduct))
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

    private fun getUserId(): String {
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }

    private fun fetchProductsFromApi(): PostgrestQueryBuilder {
        return supabase.from("products")
    }

    private fun fetchWishlistsFromApi(): PostgrestQueryBuilder {
        return supabase.from("wishlists")
    }

    private suspend fun isProductInUserWishlist(productId: String): Pair<Boolean, String> {
        val userId = getUserId()
        val columns = Columns.raw("id, user_id, product_id, products(*)")
        val wishlist = fetchWishlistsFromApi().select(columns = columns) {
            filter { and { eq("user_id", userId); eq("product_id", productId) } }
        }.decodeSingleOrNull<Wishlist>()

        val isProductInWishlist = wishlist != null
        val wishlistId = wishlist?.id ?: ""

        return isProductInWishlist to wishlistId
    }
}