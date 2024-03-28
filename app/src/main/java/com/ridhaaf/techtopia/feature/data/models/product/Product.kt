package com.ridhaaf.techtopia.feature.data.models.product

import com.ridhaaf.techtopia.feature.data.models.category.Category

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val rating: Double,
    val sold: Int,
    val stock: Int,
    val category: Category,
)
