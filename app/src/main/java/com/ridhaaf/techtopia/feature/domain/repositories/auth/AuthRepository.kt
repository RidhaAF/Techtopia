package com.ridhaaf.techtopia.feature.domain.repositories.auth

import com.ridhaaf.techtopia.core.utils.Resource
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(
        name: String,
        username: String,
        email: String,
        password: String,
    ): Flow<Resource<Email.Result?>>

    fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>

    fun signOut(): Flow<Resource<Unit>>
}