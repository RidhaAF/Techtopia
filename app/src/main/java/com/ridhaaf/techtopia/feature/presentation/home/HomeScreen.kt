package com.ridhaaf.techtopia.feature.presentation.home

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val categoriesError = state.categoriesError
    val context = LocalContext.current

    LaunchedEffect(key1 = categoriesError) {
        if (categoriesError.isNotBlank()) {
            defaultToast(context, categoriesError)
        }
    }

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
            HomeContent(state)
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
private fun HomeContent(state: HomeState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HomeBanner()
        CategoriesSection(state)
        BestSeller()
        AllProducts()
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
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
            )
    ) {
        Banner()
    }
}

@Composable
private fun BestSeller() {
    HomeProductsSection(title = "Best Seller")
}

@Composable
private fun AllProducts() {
    HomeProductsSection(title = "All Products")
}

@Composable
private fun HomeProductsSection(title: String) {
    ProductsSection(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = title,
    )
}