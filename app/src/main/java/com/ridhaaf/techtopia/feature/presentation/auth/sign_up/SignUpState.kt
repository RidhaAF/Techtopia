package com.ridhaaf.techtopia.feature.presentation.auth.sign_up

import com.ridhaaf.techtopia.feature.data.models.user.User

data class SignUpState(
    val isSignUpLoading: Boolean = false,
    val signUpSuccess: User? = null,
    val signUpError: String = "",
)
