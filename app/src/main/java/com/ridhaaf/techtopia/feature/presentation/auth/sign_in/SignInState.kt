package com.ridhaaf.techtopia.feature.presentation.auth.sign_in

data class SignInState(
    val isSignInLoading: Boolean = false,
    val signInSuccess: Unit? = null,
    val signInError: String = "",
)
