package com.ridhaaf.techtopia.feature.presentation.cart

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.CartSection
import com.ridhaaf.techtopia.core.presentation.components.DefaultErrorText
import com.ridhaaf.techtopia.core.presentation.components.DefaultProgressIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.defaultToast

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.cartError
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(CartEvent.Refresh)
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }

    Scaffold(
        topBar = { CartTopBar(navController) },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(CartEvent.Refresh) },
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(it),
        ) {
            CartContent(viewModel, state, context, navController)
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopBar(navController: NavController? = null) {
    DefaultTopAppBar(
        title = "Cart",
        showBackButton = true,
        navController = navController,
    )
}

@Composable
private fun CartContent(
    viewModel: CartViewModel,
    state: CartState,
    context: Context,
    navController: NavController? = null,
) {
    val loading = state.isCartLoading
    val cart = state.cartSuccess
    val error = state.cartError

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            DefaultProgressIndicator()
        }
    } else if (cart != null) {
        CartSection(
            viewModel = viewModel,
            state = state,
            cart = cart,
            context = context,
            navController = navController
        )
    } else {
        DefaultErrorText(
            modifier = Modifier.padding(horizontal = 16.dp),
            message = error,
        )
    }
}