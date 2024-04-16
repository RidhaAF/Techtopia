package com.ridhaaf.techtopia.feature.presentation.cart

import com.ridhaaf.techtopia.feature.data.models.cart.Cart

data class CartState(
    val isCartLoading: Boolean = false,
    val cartSuccess: Cart? = null,
    val cartError: String = "",
    val isAddProductQuantityLoading: Boolean = false,
    val addProductQuantitySuccess: Unit? = null,
    val addProductQuantityError: String = "",
    val isReduceProductQuantityLoading: Boolean = false,
    val reduceProductQuantitySuccess: Unit? = null,
    val reduceProductQuantityError: String = "",
    val isRemoveProductLoading: Boolean = false,
    val removeProductSuccess: Unit? = null,
    val removeProductError: String = "",
)
