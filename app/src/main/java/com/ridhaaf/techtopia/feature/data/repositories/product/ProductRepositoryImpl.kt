package com.ridhaaf.techtopia.feature.data.repositories.product

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.cart.Cart
import com.ridhaaf.techtopia.feature.data.models.cart.CartItem
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.domain.repositories.product.ProductRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
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

    override fun addProductToCart(productId: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val userId = supabase.auth.currentUserOrNull()?.id ?: ""
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
                fetchCartItemsFromApi().update(
                    {
                        set("quantity", newQuantity)
                    }
                ) {
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

    private fun fetchProductsFromApi(): PostgrestQueryBuilder {
        return supabase.from("products")
    }

    private fun fetchCartsFromApi(): PostgrestQueryBuilder {
        return supabase.from("carts")
    }

    private fun fetchCartItemsFromApi(): PostgrestQueryBuilder {
        return supabase.from("cart_items")
    }
}