package com.ridhaaf.techtopia.core.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.feature.data.models.cart.Cart
import com.ridhaaf.techtopia.feature.presentation.cart.CartState
import com.ridhaaf.techtopia.feature.presentation.cart.CartViewModel

@Composable
fun CartSection(
    viewModel: CartViewModel,
    state: CartState,
    cart: Cart,
    context: Context,
    navController: NavController? = null,
) {
    if (cart.items.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(cart.items) { item ->
                CartProductItem(
                    viewModel = viewModel,
                    state = state,
                    item = item,
                    context = context,
                    navController = navController,
                )
            }
        }
    } else {
        DefaultErrorText(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            message = "No items in cart",
        )
    }
}