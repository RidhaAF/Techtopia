package com.ridhaaf.techtopia.feature.data.models.product

import com.ridhaaf.techtopia.feature.data.models.category.Category
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val sold: Int = 0,
    val stock: Int = 0,
    val category: Category = Category(),
)
