package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.product.Product

@Composable
fun ProductDetailContentSection(product: Product) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ProductPrice(
            modifier = Modifier.padding(horizontal = 16.dp),
            price = product.price,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.onSecondary)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProductName(
                name = product.name,
                isLimited = false,
                style = MaterialTheme.typography.bodyLarge,
            )
            WishlistButton()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Sold ${product.sold} ",
                style = MaterialTheme.typography.bodyMedium,
            )
            ProductDetailRating(rating = product.rating.toString())
        }
        ProductDescription(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            description = product.description,
        )
        VerticalSpacer()
    }
}