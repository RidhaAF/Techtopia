package com.ridhaaf.techtopia.feature.data.models.cart

import com.ridhaaf.techtopia.feature.data.models.product.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: String = "",
    @SerialName("cart_id") val cartId: String = "",
    @SerialName("product_id") val productId: String = "",
    @SerialName("products") val product: Product = Product(),
    val quantity: Int = 0,
)