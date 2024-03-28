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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.Banner
import com.ridhaaf.techtopia.core.presentation.components.CategoriesSection
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.ProductsSection
import com.ridhaaf.techtopia.core.presentation.components.VerticalSpacer

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
) {
    Scaffold(
        topBar = { HomeTopBar() },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            HomeContent()
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
private fun HomeContent() {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HomeBanner()
        CategoriesSection()
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