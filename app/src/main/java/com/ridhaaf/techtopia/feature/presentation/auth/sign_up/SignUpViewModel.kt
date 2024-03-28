package com.ridhaaf.techtopia.feature.presentation.auth.sign_up

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    // private val useCase: SignUpUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    var name by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    private fun signUp(
        name: String,
        username: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {

        }
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.Name -> {
                name = event.name
            }

            is SignUpEvent.Username -> {
                username = event.username
            }

            is SignUpEvent.Email -> {
                email = event.email
            }

            is SignUpEvent.Password -> {
                password = event.password
            }

            is SignUpEvent.ConfirmPassword -> {
                confirmPassword = event.confirmPassword
            }

            is SignUpEvent.SignUp -> {
                if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    _state.value = SignUpState(
                        signUpError = "Please fill in all the fields"
                    )
                    return
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _state.value = SignUpState(
                        signUpError = "Please enter a valid email address",
                    )
                    return
                } else if (password.length < 8 && confirmPassword.length < 8) {
                    _state.value = SignUpState(
                        signUpError = "Password must be at least 8 characters",
                    )
                    return
                } else if (password != confirmPassword) {
                    _state.value = SignUpState(
                        signUpError = "Password and confirm password must be the same"
                    )
                    return
                }

                signUp(
                    name = name,
                    username = username,
                    email = email,
                    password = password,
                )
            }
        }
    }
}