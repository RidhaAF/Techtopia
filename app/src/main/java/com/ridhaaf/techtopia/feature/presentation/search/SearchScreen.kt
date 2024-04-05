package com.ridhaaf.techtopia.feature.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.DefaultErrorText
import com.ridhaaf.techtopia.core.presentation.components.DefaultProgressIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.ProductGrid
import com.ridhaaf.techtopia.core.presentation.components.SearchTextField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val query = viewModel.searchProducts.value

    Scaffold(
        topBar = { SearchTopBar(navController, query, viewModel) },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(SearchEvent.Search(query)) },
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(it),
        ) {
            SearchContent(state, query, navController)
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
private fun SearchTopBar(
    navController: NavController? = null,
    query: String,
    viewModel: SearchViewModel,
) {
    DefaultTopAppBar(
        title = "",
        showBackButton = true,
        navController = navController,
        navigationIcon = {
            val focusRequester = remember { FocusRequester() }

            SearchTextField(
                modifier = Modifier.padding(horizontal = 8.dp),
                focusRequester = focusRequester,
                query = query,
                viewModel = viewModel,
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        },
        isActionsEnabled = false,
    )
}

@Composable
private fun SearchContent(
    state: SearchState,
    query: String,
    navController: NavController? = null,
) {
    if (query.isEmpty()) {
        SearchEmpty(
            icon = Icons.Rounded.Search,
            msg = "Find your favorite products",
        )
    } else {
        SearchResult(state, navController)
    }
}

@Composable
private fun SearchResult(
    state: SearchState,
    navController: NavController? = null,
) {
    val loading = state.isSearchLoading
    val products = state.searchSuccess

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
        SearchEmpty(
            icon = Icons.Rounded.Cancel,
            msg = "No products found",
        )
    }
}

@Composable
private fun SearchEmpty(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    msg: String,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = msg,
            modifier = modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        DefaultErrorText(message = msg)
    }
}