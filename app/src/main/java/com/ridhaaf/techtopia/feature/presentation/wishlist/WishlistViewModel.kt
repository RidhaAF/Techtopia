package com.ridhaaf.techtopia.feature.presentation.wishlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.domain.usecases.wishlist.WishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val useCase: WishlistUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(WishlistState())
    val state: State<WishlistState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private fun refresh() {
        getWishlistByUserId()
    }

    private fun getWishlistByUserId() {
        viewModelScope.launch {
            useCase.getWishlistsByUserId().collectLatest { result ->
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

    fun onEvent(event: WishlistEvent) {
        when (event) {
            is WishlistEvent.Refresh -> {
                refresh()
            }

            is WishlistEvent.Remove -> TODO()
        }
    }
}