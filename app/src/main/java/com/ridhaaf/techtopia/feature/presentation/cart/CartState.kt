package com.ridhaaf.techtopia.feature.presentation.cart

import com.ridhaaf.techtopia.feature.data.models.cart.Cart

data class CartState(
    val isCartLoading: Boolean = false,
    val cartSuccess: Cart? = null,
    val cartError: String = "",
    val isRemoveProductLoading: Boolean = false,
    val removeProductSuccess: Unit? = null,
    val removeProductError: String = "",
)
