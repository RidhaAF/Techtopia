package com.ridhaaf.techtopia.feature.data.repositories.auth

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.repositories.auth.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    supabase: SupabaseClient,
) : AuthRepository {
    val auth = supabase.auth

    override fun signUp(
        name: String,
        username: String,
        email: String,
        password: String,
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject {
                    put("display_name", name)
                    put("username", username)
                }
            }

            emit(Resource.Success(Unit))
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

            val user = auth.signInWith(Email) {
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

            val user = auth.signOut()

            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}