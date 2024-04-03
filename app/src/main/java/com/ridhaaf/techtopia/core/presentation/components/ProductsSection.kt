package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.category.Category
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductsSection(
    modifier: Modifier = Modifier,
    title: String,
) {
    var products = listOf(
        Product("1", "iPhone 13", 9499999.0, "", "", 4.5, 100, 88, Category("1", "Phones")),
        Product("2", "MacBook Pro", 23999999.0, "", "", 4.8, 50, 15, Category("2", "Laptops")),
        Product("3", "iPad Pro", 12999999.0, "", "", 4.7, 75, 22, Category("3", "Tablets")),
        Product("4", "AirPods Pro", 3499999.0, "", "", 4.6, 200, 188, Category("4", "Accessories")),
        Product("5", "HomePod Mini", 1999999.0, "", "", 4.4, 150, 8, Category("5", "Smart Home")),
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