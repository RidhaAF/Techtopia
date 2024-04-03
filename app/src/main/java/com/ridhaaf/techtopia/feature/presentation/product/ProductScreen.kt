package com.ridhaaf.techtopia.feature.presentation.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.ridhaaf.techtopia.core.presentation.components.DefaultErrorText
import com.ridhaaf.techtopia.core.presentation.components.DefaultProgressIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.ProductGrid
import com.ridhaaf.techtopia.core.presentation.components.defaultToast

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavController? = null,
    type: String,
    categoryId: String,
    categoryName: String,
) {
    val state = viewModel.state.value
    val error = state.productsError
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProductEvent.Refresh(type, categoryId))
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }

    Scaffold(
        topBar = { ProductTopBar(navController, type, categoryName) },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(ProductEvent.Refresh(type, categoryId)) },
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(it),
        ) {
            ProductContent(state, navController)
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
private fun ProductTopBar(
    navController: NavController? = null,
    type: String,
    categoryName: String,
) {
    val title = when (type) {
        "best-seller" -> "Best Seller"
        "category" -> categoryName
        else -> "Products"
    }

    DefaultTopAppBar(
        title = title,
        showBackButton = true,
        navController = navController,
    )
}

@Composable
private fun ProductContent(
    state: ProductState,
    navController: NavController? = null,
) {
    val loading = state.isProductsLoading
    val products = state.productsSuccess
    val error = state.productsError

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                DefaultProgressIndicator()
            }
        } else if (!products.isNullOrEmpty()) {
            ProductGrid(products, navController)
        } else {
            DefaultErrorText(
                modifier = Modifier.padding(horizontal = 16.dp),
                message = error,
            )
        }
    }
}