package com.ridhaaf.techtopia.feature.data.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val email: String = "",
    @SerialName("photo_url") val photoUrl: String? = null,
)
