package com.ridhaaf.techtopia.feature.domain.usecases.category

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.category.Category
import com.ridhaaf.techtopia.feature.domain.repositories.category.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryUseCase(private val repository: CategoryRepository) {
    fun getCategories(): Flow<Resource<List<Category>>> {
        return repository.getCategories()
    }
}