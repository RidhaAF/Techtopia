package com.ridhaaf.techtopia.feature.data.models.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    @SerialName("images_url") val imagesUrl: List<String> = emptyList(),
    val description: String = "",
    val rating: Double = 0.0,
    val sold: Int = 0,
    val stock: Int = 0,
    @SerialName("category_id") val categoryId: String = "",
    val isWishlist: Boolean = false,
    val wishlistId: String = "",
)
