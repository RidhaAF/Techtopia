package com.ridhaaf.techtopia.feature.presentation.auth.sign_up

sealed class SignUpEvent {
    data class Name(val name: String) : SignUpEvent()
    data class Username(val username: String) : SignUpEvent()
    data class Email(val email: String) : SignUpEvent()
    data class Password(val password: String) : SignUpEvent()
    data class ConfirmPassword(val confirmPassword: String) : SignUpEvent()
    data object SignUp : SignUpEvent()
}