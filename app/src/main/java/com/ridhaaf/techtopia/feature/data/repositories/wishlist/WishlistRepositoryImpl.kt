package com.ridhaaf.techtopia.feature.data.repositories.wishlist

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import com.ridhaaf.techtopia.feature.domain.repositories.wishlist.WishlistRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : WishlistRepository {
    override fun getWishlistByUserId(): Flow<Resource<List<Wishlist>>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val wishlist = fetchWishlistsFromApi().select {
                filter {
                    eq("user_id", userId)
                }
            }.decodeList<Wishlist>()

            if (wishlist.isEmpty()) {
                emit(Resource.Error("No wishlist found"))
            } else {
                emit(Resource.Success(wishlist))
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
}