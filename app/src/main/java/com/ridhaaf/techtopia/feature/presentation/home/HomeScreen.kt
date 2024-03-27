package com.ridhaaf.techtopia.feature.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
) {
    val colorScheme: ColorScheme = MaterialTheme.colorScheme

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Techtopia") }, actions = {
            SearchButton()
            CartButton()
        })
    }) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            HomeContent(colorScheme)
        }
    }
}

@Composable
private fun SearchButton() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = "Search",
        )
    }
}

@Composable
private fun CartButton() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Rounded.ShoppingCart,
            contentDescription = "Cart",
        )
    }
}

@Composable
private fun HomeContent(colorScheme: ColorScheme) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(verticalScrollState),
    ) {
        VerticalSpacer()
        Banner(colorScheme)
        VerticalSpacer()
        Categories()
        VerticalSpacer()
        BestSellers()
        VerticalSpacer()
        AllProducts()
        VerticalSpacer()
    }
}

@Composable
private fun Banner(colorScheme: ColorScheme) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .aspectRatio(16f / 9f)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = colorScheme.secondary)
    )
}

@Composable
private fun Categories() {
    val categories = listOf("Phones", "Laptops", "Tablets", "Accessories", "Smart Home")

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(categories.size) { index ->
            CategoryItem(category = categories[index])
        }
    }
}

@Composable
private fun CategoryItem(category: String) {
    TextButton(onClick = { /*TODO*/ }) {
        Text(text = category)
    }
}

@Composable
private fun BestSellers() {
    ProductsSection(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = "Best Sellers",
    )
}

@Composable
private fun AllProducts() {
    ProductsSection(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = "All Products",
    )
}

@Composable
private fun ProductsSection(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "See more")
            }
        }
        VerticalSpacer(height = 4)
        ProductsList()
    }
}

@Composable
private fun ProductsList() {
    val products = listOf("Product 1", "Product 2", "Product 3", "Product 4", "Product 5")

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(products.size) { index ->
            ProductItem(product = products[index])
        }
    }
}

@Composable
private fun ProductItem(product: String) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    )
}