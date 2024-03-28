package com.ridhaaf.techtopia.feature.presentation.auth.sign_in

import com.ridhaaf.techtopia.feature.data.models.user.User

data class SignInState(
    val isSignInLoading: Boolean = false,
    val signInSuccess: User? = null,
    val signInError: String = "",
)
