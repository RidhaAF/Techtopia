package com.ridhaaf.techtopia.core.presentation.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ridhaaf.techtopia.feature.data.models.product.Product
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailEvent
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailViewModel

@Composable
fun WishlistButton(
    viewModel: ProductDetailViewModel,
    context: Context,
    product: Product,
) {
    val state = viewModel.state.value
    val success = state.wishlistSuccess
    val error = state.wishlistError

    var isWishlist by remember { mutableStateOf(product.isWishlist) }
    val (icon, desc) = if (isWishlist) {
        Icons.Rounded.Favorite to "Remove from wishlist"
    } else {
        Icons.Rounded.FavoriteBorder to "Add to wishlist"
    }

    ActionButton(
        onClick = {
            isWishlist = !isWishlist
            val productId = product.id
            val wishlistId = product.wishlistId

            if (isWishlist) {
                viewModel.onEvent(ProductDetailEvent.AddToWishlist(productId))
            } else {
                viewModel.onEvent(
                    ProductDetailEvent.RemoveFromWishlist(
                        productId = productId,
                        wishlistId = wishlistId,
                    )
                )
            }
        },
        icon = icon,
        desc = desc,
    )

    LaunchedEffect(key1 = success != null, key2 = error) {
        if (success != null) {
            if (isWishlist) {
                defaultToast(context, "Successfully added to wishlist")
            } else {
                defaultToast(context, "Successfully removed from wishlist")
            }
        } else if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }
}