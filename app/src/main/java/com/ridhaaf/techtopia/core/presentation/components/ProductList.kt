package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductsList(
    products: List<Product>,
    navController: NavController? = null,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        val itemLength = if (products.size > 10) 10 else products.size

        items(itemLength) { i ->
            val product = products[i]

            ProductItem(
                product = product,
                navController = navController,
            )
        }
    }
}