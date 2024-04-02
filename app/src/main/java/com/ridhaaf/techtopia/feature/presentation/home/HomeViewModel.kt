package com.ridhaaf.techtopia.feature.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.category.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        refresh()
    }

    private fun refresh() {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryUseCase.getCategories().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isCategoriesLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isCategoriesLoading = false,
                            categoriesSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isCategoriesLoading = false,
                            categoriesSuccess = emptyList(),
                            categoriesError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> {
                refresh()
            }
        }
    }
}