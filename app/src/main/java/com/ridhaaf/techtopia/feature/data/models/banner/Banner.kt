package com.ridhaaf.techtopia.feature.data.models.banner

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    val id: String = "",
    @SerialName("image_url") val imageUrl: String = "",
)