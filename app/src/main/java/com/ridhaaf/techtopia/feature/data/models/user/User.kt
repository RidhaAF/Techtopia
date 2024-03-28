package com.ridhaaf.techtopia.feature.data.models.user

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val photoUrl: String,
)
