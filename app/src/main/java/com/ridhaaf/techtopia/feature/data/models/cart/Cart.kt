package com.ridhaaf.techtopia.feature.data.models.cart

import com.ridhaaf.techtopia.feature.data.models.product.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    val items: List<CartItem> = emptyList(),
)
