package com.ridhaaf.techtopia.feature.presentation.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.product.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private fun refresh(type: String = "all", categoryId: String) {
        when (type) {
            "best-seller" -> {
                getBestSellingProducts()
            }

            "category" -> {
                getProductsByCategory(categoryId)
            }

            else -> {
                getProducts()
            }
        }
    }

    private fun getBestSellingProducts() {
        viewModelScope.launch {
            productUseCase.getBestSellingProducts().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = false,
                            productsSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = false,
                            productsSuccess = emptyList(),
                            productsError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            productUseCase.getProducts().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = false,
                            productsSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = false,
                            productsSuccess = emptyList(),
                            productsError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun getProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            productUseCase.getProductsByCategory(categoryId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = false,
                            productsSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isProductsLoading = false,
                            productsSuccess = emptyList(),
                            productsError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.Refresh -> {
                val type = event.type
                val categoryId = event.categoryId

                refresh(type, categoryId)
            }
        }
    }
}