package com.ridhaaf.techtopia.feature.presentation.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.Banner
import com.ridhaaf.techtopia.core.presentation.components.CategoriesSection
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.ProductsSection
import com.ridhaaf.techtopia.core.presentation.components.VerticalSpacer
import com.ridhaaf.techtopia.core.presentation.components.defaultToast
import com.ridhaaf.techtopia.feature.data.models.product.Product

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    Scaffold(
        topBar = { HomeTopBar() },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
        )
        val verticalScrollState = rememberScrollState()

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .verticalScroll(verticalScrollState)
                .padding(it),
        ) {
            HomeContent(state, context, navController)
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
private fun HomeTopBar() {
    DefaultTopAppBar(
        title = "Techtopia",
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
        ),
    )
}

@Composable
private fun HomeContent(
    state: HomeState,
    context: Context,
    navController: NavController? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HomeBanner()
        Categories(state, context, navController)
        BestSeller(state, context, navController)
        AllProducts(state, context, navController)
        VerticalSpacer()
    }
}

@Composable
private fun HomeBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.inversePrimary,
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp,
                ),
            )
    ) {
        Banner()
    }
}

@Composable
private fun Categories(
    state: HomeState,
    context: Context,
    navController: NavController? = null,
) {
    val error = state.categoriesError

    CategoriesSection(state, navController)

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }
}

@Composable
private fun BestSeller(
    state: HomeState,
    context: Context,
    navController: NavController? = null,
) {
    val loading = state.isBestSellerLoading
    val products = state.bestSellerSuccess
    val error = state.bestSellerError

    HomeProductsSection(
        loading = loading,
        products = products,
        error = error,
        title = "Best Seller",
        navController = navController,
    )

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }
}

@Composable
private fun AllProducts(
    state: HomeState,
    context: Context,
    navController: NavController? = null,
) {
    val loading = state.isProductsLoading
    val products = state.productsSuccess
    val error = state.productsError

    HomeProductsSection(
        loading = loading,
        products = products,
        error = error,
        title = "All Products",
        navController = navController,
    )

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }
}

@Composable
private fun HomeProductsSection(
    loading: Boolean,
    products: List<Product>?,
    error: String,
    title: String,
    navController: NavController? = null,
) {
    ProductsSection(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        loading = loading,
        products = products,
        error = error,
        title = title,
        navController = navController,
    )
}