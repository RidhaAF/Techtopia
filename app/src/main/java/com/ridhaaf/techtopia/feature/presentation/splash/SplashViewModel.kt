package com.ridhaaf.techtopia.feature.presentation.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.presentation.routes.Routes
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: AuthUseCase,
): ViewModel() {
    private var isAuthenticated by mutableStateOf(false)

    private fun isAuth(): Boolean {
        viewModelScope.launch {
            useCase.isAuth().collect { result ->
                isAuthenticated = when (result) {
                    is Resource.Success -> {
                        result.data != null
                    }

                    else -> {
                        false
                    }
                }
            }
        }
        return isAuthenticated
    }

    fun initialRoute(): String {
        return if (isAuth()) {
            Routes.HOME
        } else {
            Routes.SIGN_IN
        }
    }
}