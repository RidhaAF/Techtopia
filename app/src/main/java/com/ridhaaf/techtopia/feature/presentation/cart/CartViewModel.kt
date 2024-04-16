package com.ridhaaf.techtopia.feature.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.cart.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(CartState())
    val state: State<CartState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private fun refresh() {
        getCart()
    }

    private fun getCart() {
        viewModelScope.launch {
            useCase.getCart().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isCartLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isCartLoading = false,
                            cartSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isCartLoading = false,
                            cartSuccess = null,
                            cartError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun addProductQuantity(productId: String) {
        viewModelScope.launch {
            useCase.addProductQuantity(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isAddProductQuantityLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isAddProductQuantityLoading = false,
                            addProductQuantitySuccess = result.data,
                        )
                        refresh()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isAddProductQuantityLoading = false,
                            addProductQuantitySuccess = null,
                            addProductQuantityError = result.message
                                ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun reduceProductQuantity(productId: String) {
        viewModelScope.launch {
            useCase.reduceProductQuantity(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isReduceProductQuantityLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isReduceProductQuantityLoading = false,
                            reduceProductQuantitySuccess = result.data,
                        )
                        refresh()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isReduceProductQuantityLoading = false,
                            reduceProductQuantitySuccess = null,
                            reduceProductQuantityError = result.message
                                ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun removeProduct(productId: String) {
        viewModelScope.launch {
            useCase.removeProductFromCart(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isRemoveProductLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isRemoveProductLoading = false,
                            removeProductSuccess = result.data,
                        )
                        refresh()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isRemoveProductLoading = false,
                            removeProductSuccess = null,
                            removeProductError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.Refresh -> {
                refresh()
            }

            is CartEvent.AddQuantity -> {
                addProductQuantity(event.productId)
            }

            is CartEvent.ReduceQuantity -> {
                reduceProductQuantity(event.productId)
            }

            is CartEvent.RemoveProduct -> {
                removeProduct(event.productId)
            }
        }
    }
}