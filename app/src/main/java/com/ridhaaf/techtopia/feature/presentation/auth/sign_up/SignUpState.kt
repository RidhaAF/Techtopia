package com.ridhaaf.techtopia.feature.presentation.auth.sign_up

import io.github.jan.supabase.gotrue.providers.builtin.Email

data class SignUpState(
    val isSignUpLoading: Boolean = false,
    val signUpSuccess: Email.Result? = null,
    val signUpError: String = "",
)
