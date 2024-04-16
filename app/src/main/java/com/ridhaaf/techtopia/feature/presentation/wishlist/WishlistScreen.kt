package com.ridhaaf.techtopia.feature.presentation.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.DefaultErrorText
import com.ridhaaf.techtopia.core.presentation.components.DefaultProgressIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.WishlistGrid

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    viewModel: WishlistViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.onEvent(WishlistEvent.Refresh)
    }

    Scaffold(
        topBar = { WishlistTopBar(navController) },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(WishlistEvent.Refresh) },
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(it),
        ) {
            WishlistContent(
                viewModel = viewModel,
                state = state,
                navController = navController,
            )
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
private fun WishlistTopBar(navController: NavController? = null) {
    DefaultTopAppBar(
        title = "Wishlist",
        navController = navController,
    )
}

@Composable
private fun WishlistContent(
    viewModel: WishlistViewModel,
    state: WishlistState,
    navController: NavController? = null,
) {
    val loading = state.isWishlistLoading
    val wishlists = state.wishlistSuccess
    val error = state.wishlistError

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            DefaultProgressIndicator()
        }
    } else if (!wishlists.isNullOrEmpty()) {
        WishlistGrid(
            viewModel = viewModel,
            wishlists = wishlists,
            navController = navController,
        )
    } else {
        DefaultErrorText(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            message = error,
        )
    }
}