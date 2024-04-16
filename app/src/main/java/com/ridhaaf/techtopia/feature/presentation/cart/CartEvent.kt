package com.ridhaaf.techtopia.feature.presentation.cart

sealed class CartEvent {
    data object Refresh : CartEvent()
    data class AddQuantity(val productId: String) : CartEvent()
    data class ReduceQuantity(val productId: String) : CartEvent()
    data class RemoveProduct(val productId: String) : CartEvent()
    data object ResetRemoveProductState : CartEvent()
}
