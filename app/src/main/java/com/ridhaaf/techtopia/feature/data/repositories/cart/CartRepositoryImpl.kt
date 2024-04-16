package com.ridhaaf.techtopia.feature.data.repositories.cart

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.cart.Cart
import com.ridhaaf.techtopia.feature.data.models.cart.CartItem
import com.ridhaaf.techtopia.feature.domain.repositories.cart.CartRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : CartRepository {
    override fun addProductToCart(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val cartId = getOrCreateCartId(userId)
            val cartItem = fetchCartItem(productId, cartId)

            if (cartItem == null) {
                val newCartItem = CartItem(
                    cartId = cartId,
                    productId = productId,
                    quantity = 1,
                )
                fetchCartItemsFromApi().insert(newCartItem)
            } else {
                val newQuantity = cartItem.quantity + 1
                fetchCartItemsFromApi().update({
                    set("quantity", newQuantity)
                }) {
                    filter {
                        eq("id", cartItem.id)
                        eq("cart_id", cartId)
                        eq("product_id", productId)
                    }
                }
            }

            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            println("error: ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun removeProductFromCart(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val cartId = getOrCreateCartId(userId)
            val cartItem = fetchCartItem(productId, cartId)

            if (cartItem == null) {
                emit(Resource.Error("Product not found in cart"))
            } else {
                fetchCartItemsFromApi().delete {
                    filter {
                        eq("id", cartItem.id)
                    }
                }

                emit(Resource.Success(Unit))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getCart(): Flow<Resource<Cart>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val cartId = getOrCreateCartId(userId)
            val columns = Columns.raw(
                "id, cart_id, products(*), quantity"
            )
            val cartItems = fetchCartItemsFromApi().select(
                columns = columns
            ) {
                filter {
                    eq("cart_id", cartId)
                }
            }.decodeList<CartItem>().reversed()

            val cart = Cart(
                id = cartId,
                userId = userId,
                items = cartItems,
            )

            emit(Resource.Success(cart))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun addProductQuantity(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val cartId = getOrCreateCartId(userId)
            val cartItem = fetchCartItem(productId, cartId)

            if (cartItem == null) {
                emit(Resource.Error("Product not found in cart"))
            } else {
                val newQuantity = cartItem.quantity + 1
                fetchCartItemsFromApi().update({
                    set("quantity", newQuantity)
                }) {
                    filter {
                        eq("id", cartItem.id)
                        eq("cart_id", cartId)
                        eq("product_id", productId)
                    }
                }

                emit(Resource.Success(Unit))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun reduceProductQuantity(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val userId = getUserId()
            val cartId = getOrCreateCartId(userId)
            val cartItem = fetchCartItem(productId, cartId)

            if (cartItem == null) {
                emit(Resource.Error("Product not found in cart"))
            } else {
                val newQuantity = cartItem.quantity - 1
                fetchCartItemsFromApi().update({
                    set("quantity", newQuantity)
                }) {
                    filter {
                        eq("id", cartItem.id)
                        eq("cart_id", cartId)
                        eq("product_id", productId)
                    }
                }

                emit(Resource.Success(Unit))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun getOrCreateCartId(userId: String): String {
        val carts = fetchCartsFromApi().select {
            filter {
                eq("user_id", userId)
            }
        }.decodeList<Cart>()

        if (carts.isEmpty()) {
            val newCart = mapOf("user_id" to userId)
            val insertedCart = fetchCartsFromApi().insert(newCart)
            return insertedCart.decodeSingle<Cart>().id
        }

        return carts.first().id
    }

    private suspend fun fetchCartItem(productId: String, cartId: String): CartItem? {
        return fetchCartItemsFromApi().select {
            filter {
                eq("cart_id", cartId)
                eq("product_id", productId)
            }
        }.decodeList<CartItem>().firstOrNull()
    }

    private fun getUserId(): String {
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }

    private fun fetchCartsFromApi(): PostgrestQueryBuilder {
        return supabase.from("carts")
    }

    private fun fetchCartItemsFromApi(): PostgrestQueryBuilder {
        return supabase.from("cart_items")
    }
}