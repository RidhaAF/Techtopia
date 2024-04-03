package com.ridhaaf.techtopia.di

import com.ridhaaf.techtopia.feature.data.repositories.auth.AuthRepositoryImpl
import com.ridhaaf.techtopia.feature.data.repositories.category.CategoryRepositoryImpl
import com.ridhaaf.techtopia.feature.data.repositories.product.ProductRepositoryImpl
import com.ridhaaf.techtopia.feature.domain.repositories.auth.AuthRepository
import com.ridhaaf.techtopia.feature.domain.repositories.category.CategoryRepository
import com.ridhaaf.techtopia.feature.domain.repositories.product.ProductRepository
import com.ridhaaf.techtopia.feature.domain.usecases.auth.AuthUseCase
import com.ridhaaf.techtopia.feature.domain.usecases.category.CategoryUseCase
import com.ridhaaf.techtopia.feature.domain.usecases.product.ProductUseCase
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
            install(Auth) {
                alwaysAutoRefresh = true
            }
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

    @Provides
    @Singleton
    fun provideCategoryUseCase(
        repository: CategoryRepository,
    ): CategoryUseCase {
        return CategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        supabase: SupabaseClient,
    ): CategoryRepository {
        return CategoryRepositoryImpl(supabase)
    }

    @Provides
    @Singleton
    fun provideProductUseCase(
        repository: ProductRepository,
    ): ProductUseCase {
        return ProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        supabase: SupabaseClient,
    ): ProductRepository {
        return ProductRepositoryImpl(supabase)
    }
}