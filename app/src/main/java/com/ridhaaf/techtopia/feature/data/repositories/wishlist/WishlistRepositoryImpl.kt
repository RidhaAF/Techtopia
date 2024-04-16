package com.ridhaaf.techtopia.feature.data.repositories.wishlist

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import com.ridhaaf.techtopia.feature.domain.repositories.wishlist.WishlistRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : WishlistRepository {
    override fun getWishlistsByUserId(): Flow<Resource<List<Wishlist>>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val wishlists = fetchWishlistsFromApi().select {
                filter {
                    eq("user_id", userId)
                }
            }.decodeList<Wishlist>()

            if (wishlists.isEmpty()) {
                emit(Resource.Error("No wishlist found"))
            } else {
                val wishlistsWithProduct = wishlists.map { item ->
                    val product = fetchProductById(item.productId)
                    item.copy(product = product)
                }
                emit(Resource.Success(wishlistsWithProduct))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun addWishlist(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val wishlist = Wishlist(
                userId = userId,
                productId = productId,
            )
            fetchWishlistsFromApi().insert(wishlist)

            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun removeWishlist(id: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            fetchWishlistsFromApi().delete {
                filter {
                    eq("id", id)
                }
            }

            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }

    }

    private fun getUserId(): String {
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }

    private fun fetchWishlistsFromApi(): PostgrestQueryBuilder {
        return supabase.from("wishlists")
    }

    private fun fetchProductsFromApi(): PostgrestQueryBuilder {
        return supabase.from("products")
    }

    private suspend fun fetchProductById(productId: String): Product {
        val product = fetchProductsFromApi().select {
            filter {
                eq("id", productId)
            }
        }.decodeSingle<Product>()

        val (isProductInWishlist, wishlistId) = isProductInUserWishlist(productId)

        return product.copy(isWishlist = isProductInWishlist, wishlistId = wishlistId)
    }

    private suspend fun isProductInUserWishlist(productId: String): Pair<Boolean, String> {
        val userId = getUserId()
        val columns = Columns.raw("(*), products(*)")
        val wishlist = fetchWishlistsFromApi().select(columns) {
            filter {
                and {
                    eq("user_id", userId)
                    eq("product_id", productId)
                }
            }
        }.decodeSingleOrNull<Wishlist>()

        val isProductInWishlist = wishlist != null
        val wishlistId = wishlist?.id ?: ""

        return isProductInWishlist to wishlistId
    }
}