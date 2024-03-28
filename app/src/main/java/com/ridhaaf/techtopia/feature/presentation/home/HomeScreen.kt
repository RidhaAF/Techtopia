package com.ridhaaf.techtopia.feature.presentation.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.VerticalSpacer
import com.ridhaaf.techtopia.core.utils.currencyFormatter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
) {
    Scaffold(
        topBar = { TopBar() },
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
private fun TopBar() {
    TopAppBar(
        title = { Text(text = "Techtopia") },
        actions = {
            SearchButton()
            CartButton()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
        ),
    )
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
private fun HomeContent() {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Banner()
        Categories()
        BestSeller()
        AllProducts()
        VerticalSpacer()
    }
}

@Composable
private fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.inversePrimary,
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .aspectRatio(16f / 9f)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.secondary)
        )
    }
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
    TextButton(
        onClick = { /*TODO*/ },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Text(text = category)
    }
}

@Composable
private fun BestSeller() {
    ProductsSection(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = "Best Seller",
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
    var products = listOf(
        Product("iPhone 13", 4.5, 100, 9499999.0),
        Product("MacBook Pro", 4.8, 50, 23999999.0),
        Product("iPad Pro", 4.7, 75, 12999999.0),
        Product("AirPods Pro", 4.6, 200, 3499999.0),
        Product("HomePod Mini", 4.4, 150, 1999999.0),
    )
    if (title == "Best Seller") {
        products = products.sortedByDescending { it.sold }.take(3)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
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
        ProductsList(products = products)
    }
}

@Composable
private fun ProductsList(products: List<Product>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
private fun ProductItem(product: Product) {
    Card {
        ProductImage()
        ProductDetail(
            name = product.name,
            rating = product.rating,
            sold = product.sold,
            price = product.price,
        )
    }
}

@Composable
private fun ProductImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .aspectRatio(1f)
            .background(color = MaterialTheme.colorScheme.secondary)
    )
}

@Composable
private fun ProductDetail(
    name: String,
    rating: Double,
    sold: Int,
    price: Double,
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        ProductName(name = name)
        ProductRating(rating = rating, sold = sold)
        ProductPrice(price = price)
    }
}

@Composable
private fun ProductName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
private fun ProductRating(rating: Double, sold: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "Rating",
            tint = Color.Yellow,
        )
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = " | $sold sold",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun ProductPrice(price: Double) {
    Text(
        text = currencyFormatter(price),
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
        ),
    )
}

data class Product(
    val name: String,
    val rating: Double,
    val sold: Int,
    val price: Double,
)