package com.ridhaaf.techtopia.feature.presentation.auth.sign_in

sealed class SignInEvent {
    data class Email(val email: String) : SignInEvent()
    data class Password(val password: String) : SignInEvent()
    data object SignIn : SignInEvent()
}