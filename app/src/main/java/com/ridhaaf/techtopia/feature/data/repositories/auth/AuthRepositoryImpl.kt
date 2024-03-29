package com.ridhaaf.techtopia.feature.data.repositories.auth

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.repositories.auth.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : AuthRepository {
    override fun signUp(
        name: String,
        username: String,
        email: String,
        password: String,
    ): Flow<Resource<Email.Result?>> = flow {
        try {
            emit(Resource.Loading())

            val user = supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

             val user = supabase.auth.signInWith(Email) {
                 this.email = email
                 this.password = password
             }

            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun signOut(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

             val user = supabase.auth.signOut()

            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}