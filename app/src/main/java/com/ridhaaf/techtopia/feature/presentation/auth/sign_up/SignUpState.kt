package com.ridhaaf.techtopia.feature.presentation.auth.sign_up

data class SignUpState(
    val isSignUpLoading: Boolean = false,
    val signUpSuccess: Unit? = null,
    val signUpError: String = "",
)
