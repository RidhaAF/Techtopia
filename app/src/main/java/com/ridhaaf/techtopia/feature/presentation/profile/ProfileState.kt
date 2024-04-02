package com.ridhaaf.techtopia.feature.presentation.profile

import com.ridhaaf.techtopia.feature.data.models.user.User

data class ProfileState(
    val isUserLoading: Boolean = false,
    val userSuccess: User? = null,
    val userError: String = "",
    val isSignOutLoading: Boolean = false,
    val signOutSuccess: Boolean = false,
    val signOutError: String = "",
)
