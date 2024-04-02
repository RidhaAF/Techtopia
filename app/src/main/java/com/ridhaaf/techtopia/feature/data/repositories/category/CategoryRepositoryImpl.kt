package com.ridhaaf.techtopia.feature.data.repositories.category

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.category.Category
import com.ridhaaf.techtopia.feature.domain.repositories.category.CategoryRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : CategoryRepository {
    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())

            val categories = fetchCategoriesFromApi()

            if (categories.isEmpty()) {
                emit(Resource.Error("No categories found"))
            } else {
                emit(Resource.Success(categories))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun fetchCategoriesFromApi(): List<Category> {
        return supabase.from("categories").select().decodeList<Category>()
    }
}