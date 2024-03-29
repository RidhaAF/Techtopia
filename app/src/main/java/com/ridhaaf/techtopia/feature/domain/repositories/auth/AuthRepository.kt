package com.ridhaaf.techtopia.feature.domain.repositories.auth

import com.ridhaaf.techtopia.core.utils.Resource
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
}