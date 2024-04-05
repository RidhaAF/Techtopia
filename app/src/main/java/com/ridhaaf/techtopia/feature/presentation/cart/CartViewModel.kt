package com.ridhaaf.techtopia.feature.presentation.cart

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
class CartViewModel @Inject constructor(
    private val useCase: ProductUseCase,
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

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.Refresh -> {
                refresh()
            }

            is CartEvent.AddQuantity -> TODO()
            is CartEvent.ReduceQuantity -> TODO()
            is CartEvent.RemoveProduct -> TODO()
        }
    }
}