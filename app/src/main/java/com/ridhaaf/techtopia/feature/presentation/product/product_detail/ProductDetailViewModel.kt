package com.ridhaaf.techtopia.feature.presentation.product.product_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.cart.CartUseCase
import com.ridhaaf.techtopia.feature.domain.usecases.product.ProductUseCase
import com.ridhaaf.techtopia.feature.domain.usecases.wishlist.WishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val cartUseCase: CartUseCase,
    private val wishlistUseCase: WishlistUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(ProductDetailState())
    val state: State<ProductDetailState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private fun refresh(id: String) {
        getProductById(id)
    }

    private fun getProductById(id: String) {
        viewModelScope.launch {
            productUseCase.getProductById(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isProductLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isProductLoading = false,
                            productSuccess = result.data,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isProductLoading = false,
                            productSuccess = null,
                            productError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun addProductToCart(productId: String) {
        viewModelScope.launch {
            cartUseCase.addProductToCart(productId).collect { result ->
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

    private fun addProductToWishlist(productId: String) {
        viewModelScope.launch {
            wishlistUseCase.addWishlist(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isWishlistLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isWishlistLoading = false,
                            wishlistSuccess = result.data,
                        )
                        refresh(productId)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isWishlistLoading = false,
                            wishlistSuccess = null,
                            wishlistError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    private fun removeProductFromWishlist(
        productId: String,
        wishlistId: String,
    ) {
        viewModelScope.launch {
            wishlistUseCase.removeWishlist(wishlistId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isWishlistLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isWishlistLoading = false,
                            wishlistSuccess = result.data,
                        )
                        refresh(productId)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isWishlistLoading = false,
                            wishlistSuccess = null,
                            wishlistError = result.message ?: "Oops, something went wrong!",
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.Refresh -> {
                val id = event.id

                refresh(id)
            }

            is ProductDetailEvent.AddToCart -> {
                val id = event.productId

                addProductToCart(id)
            }

            is ProductDetailEvent.AddToWishlist -> {
                val id = event.productId

                addProductToWishlist(id)
            }

            is ProductDetailEvent.RemoveFromWishlist -> {
                val id = event.productId
                val wishlistId = event.wishlistId

                removeProductFromWishlist(
                    productId = id,
                    wishlistId = wishlistId,
                )
            }
        }
    }
}