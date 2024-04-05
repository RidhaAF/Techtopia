package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    navController: NavController? = null,
) {
    Card(
        modifier = modifier.clickable {
            val id = product.id
            navController?.navigate("product/$id")
        },
    ) {
        Box(
            modifier = Modifier.aspectRatio(1f),
        ) {
            ProductImage(
                modifier = Modifier
                    .fillMaxSize()
                    .width(160.dp),
                product = product,
            )
        }
        ProductDetail(product)
    }
}