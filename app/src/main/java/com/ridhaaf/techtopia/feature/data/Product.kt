package com.ridhaaf.techtopia.feature.data

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val rating: Double,
    val sold: Int,
    val category: Category,
)
