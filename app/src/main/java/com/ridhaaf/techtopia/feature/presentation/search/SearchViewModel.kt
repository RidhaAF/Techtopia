package com.ridhaaf.techtopia.feature.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.product.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: ProductUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _searchProducts = mutableStateOf("")
    val searchProducts: State<String> = _searchProducts

    private var searchJob: Job? = null

    private fun refresh(query: String) {
        searchProducts(query)
    }

    private fun searchProducts(query: String) {
        _searchProducts.value = query

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            useCase.searchProducts(query).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isSearchLoading = true,
                            searchSuccess = null,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isSearchLoading = false,
                            searchSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isSearchLoading = false,
                            searchSuccess = result.data,
                            searchError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> {
                refresh(event.query)
            }
        }
    }
}