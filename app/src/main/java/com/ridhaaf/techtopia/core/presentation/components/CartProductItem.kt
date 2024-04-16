package com.ridhaaf.techtopia.core.presentation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.routes.Routes
import com.ridhaaf.techtopia.feature.data.models.cart.CartItem
import com.ridhaaf.techtopia.feature.presentation.cart.CartEvent
import com.ridhaaf.techtopia.feature.presentation.cart.CartState
import com.ridhaaf.techtopia.feature.presentation.cart.CartViewModel

@Composable
fun CartProductItem(
    viewModel: CartViewModel,
    state: CartState,
    item: CartItem,
    context: Context,
    navController: NavController? = null,
) {
    val success = state.removeProductSuccess
    val error = state.removeProductError

    val product = item.product
    val id = product.id
    val name = product.name
    val price = product.price
    val quantity = item.quantity.toString()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                navController?.navigate("${Routes.PRODUCT}/$id")
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProductImage(
            modifier = Modifier
                .width(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            product = item.product,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ProductName(
                name,
                maxLines = 50,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
            )
            ProductPrice(
                price,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            CartProductQuantity(
                viewModel = viewModel,
                context = context,
                product = product,
                quantity = quantity,
            )
        }
        ActionButton(
            onClick = { viewModel.onEvent(CartEvent.RemoveProduct(id)) },
            icon = Icons.Rounded.Delete,
            desc = "Remove item",
            tint = MaterialTheme.colorScheme.error,
        )

        LaunchedEffect(key1 = success != null, key2 = error) {
            if (success != null) {
                defaultToast(context, "Item removed from cart")
                viewModel.onEvent(CartEvent.ResetRemoveProductState)
            } else if (error.isNotBlank()) {
                defaultToast(context, error)
            }
        }
    }
}