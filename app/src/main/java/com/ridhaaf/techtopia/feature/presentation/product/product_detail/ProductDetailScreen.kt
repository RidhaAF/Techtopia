package com.ridhaaf.techtopia.feature.presentation.product.product_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
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
import com.ridhaaf.techtopia.core.presentation.components.AddToCartSection
import com.ridhaaf.techtopia.core.presentation.components.DefaultErrorText
import com.ridhaaf.techtopia.core.presentation.components.DefaultProgressIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultRefreshIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.ProductDetailSection
import com.ridhaaf.techtopia.core.presentation.components.defaultToast

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    navController: NavController? = null,
    id: String,
) {
    val state = viewModel.state.value
    val error = state.productError
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(ProductDetailEvent.Refresh(id))
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }

    Scaffold(
        topBar = { ProductDetailTopBar(navController) },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(ProductDetailEvent.Refresh(id)) },
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(it),
        ) {
            ProductDetailContent(viewModel, state)
            DefaultRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailTopBar(navController: NavController? = null) {
    DefaultTopAppBar(
        title = "",
        showBackButton = true,
        navController = navController,
    )
}

@Composable
private fun ProductDetailContent(
    viewModel: ProductDetailViewModel,
    state: ProductDetailState,
) {
    val loading = state.isProductLoading
    val product = state.productSuccess
    val error = state.productError
    val context = LocalContext.current

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            DefaultProgressIndicator()
        }
    } else if (product != null) {
        Box {
            ProductDetailSection(
                viewModel = viewModel,
                context = context,
                product = product,
            )
            Column {
                Spacer(modifier = Modifier.weight(1f))
                AddToCartSection(
                    viewModel = viewModel,
                    state = state,
                    context = context,
                    product = product,
                )
            }
        }
    } else {
        DefaultErrorText(
            modifier = Modifier.padding(horizontal = 16.dp),
            message = error,
        )
    }
}