package com.ridhaaf.techtopia.feature.domain.repositories.auth

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.user.User
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(
        name: String,
        username: String,
        email: String,
        password: String,
    ): Flow<Resource<Unit>>

    fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>

    fun signOut(): Flow<Resource<Unit>>

    fun isAuth(): Flow<Resource<UserSession?>>

    fun getCurrentUser(): Flow<Resource<User>>
}