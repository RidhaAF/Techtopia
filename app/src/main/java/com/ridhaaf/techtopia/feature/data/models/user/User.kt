package com.ridhaaf.techtopia.feature.data.models.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String = "",
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var photoUrl: String? = null,
)
