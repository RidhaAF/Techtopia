package com.ridhaaf.techtopia.feature.domain.usecases.auth

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.repositories.auth.AuthRepository
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow

class AuthUseCase(private val repository: AuthRepository) {
    fun signUp(
        name: String,
        username: String,
        email: String,
        password: String,
    ): Flow<Resource<Email.Result?>> {
        return repository.signUp(name, username, email, password)
    }

    fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return repository.signIn(email, password)
    }

    fun signOut(): Flow<Resource<Unit>> {
        return repository.signOut()
    }
}