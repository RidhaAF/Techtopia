package com.ridhaaf.techtopia.feature.data.models.wishlist

import com.ridhaaf.techtopia.feature.data.models.product.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wishlist(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("product_id") val productId: String = "",
    @SerialName("products") val product: Product = Product(),
)
