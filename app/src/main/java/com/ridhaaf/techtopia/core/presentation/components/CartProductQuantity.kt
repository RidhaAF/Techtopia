package com.ridhaaf.techtopia.core.presentation.components

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.presentation.cart.CartEvent
import com.ridhaaf.techtopia.feature.presentation.cart.CartViewModel

@Composable
fun CartProductQuantity(
    viewModel: CartViewModel,
    context: Context,
    product: Product,
    quantity: String,
) {
    val id = product.id
    val stock = product.stock

    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProductQuantityIcon(
            imageVector = Icons.Rounded.Remove,
            desc = "Reduce",
            onClick = {
                if (quantity == "1") {
                    return@ProductQuantityIcon
                }
                viewModel.onEvent(CartEvent.ReduceQuantity(id))
            },
        )
        Text(
            quantity,
            style = MaterialTheme.typography.bodySmall,
        )
        ProductQuantityIcon(
            imageVector = Icons.Rounded.Add,
            desc = "Increase",
            onClick = {
                if (quantity.toInt() == stock) {
                    defaultToast(context, "Stock is limited")
                    return@ProductQuantityIcon
                }
                viewModel.onEvent(CartEvent.AddQuantity(id))
            },
        )
    }
}