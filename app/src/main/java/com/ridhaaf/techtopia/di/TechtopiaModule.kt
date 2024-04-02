package com.ridhaaf.techtopia.di

import com.ridhaaf.techtopia.feature.data.repositories.auth.AuthRepositoryImpl
import com.ridhaaf.techtopia.feature.domain.repositories.auth.AuthRepository
import com.ridhaaf.techtopia.feature.domain.usecases.auth.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TechtopiaModule {
    @Provides
    @Singleton
    fun provideDotEnv(): Dotenv {
        return dotenv {
            directory = "/assets"
            filename = "env"
        }
    }

    @Provides
    @Singleton
    fun provideSupabase(dotenv: Dotenv): SupabaseClient {
        val supabaseUrl = dotenv["SUPABASE_URL"]
        val supabaseApiKey = dotenv["SUPABASE_API_KEY"]

        return createSupabaseClient(supabaseUrl, supabaseApiKey) {
            install(Auth)
            install(Postgrest)
            defaultSerializer = KotlinXSerializer(
                Json {
                    ignoreUnknownKeys = true
                },
            )
        }
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(
        repository: AuthRepository,
    ): AuthUseCase {
        return AuthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        supabase: SupabaseClient,
    ): AuthRepository {
        return AuthRepositoryImpl(supabase)
    }
}