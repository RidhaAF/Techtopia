package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import com.ridhaaf.techtopia.feature.presentation.wishlist.WishlistEvent
import com.ridhaaf.techtopia.feature.presentation.wishlist.WishlistViewModel

@Composable
fun RemoveWishlistButton(
    viewModel: WishlistViewModel,
    wishlist: Wishlist,
) {
    ActionButton(
        onClick = { viewModel.onEvent(WishlistEvent.Remove(wishlist.id)) },
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        icon = Icons.Rounded.Delete,
        desc = "Remove from wishlist",
        tint = MaterialTheme.colorScheme.errorContainer,
    )
}