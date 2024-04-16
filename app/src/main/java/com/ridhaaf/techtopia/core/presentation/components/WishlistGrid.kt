package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.feature.data.models.wishlist.Wishlist
import com.ridhaaf.techtopia.feature.presentation.wishlist.WishlistViewModel

@Composable
fun WishlistGrid(
    viewModel: WishlistViewModel,
    wishlists: List<Wishlist>,
    navController: NavController? = null,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(wishlists) { wishlist ->
            Box {
                ProductItem(
                    product = wishlist.product,
                    navController = navController,
                )
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    RemoveWishlistButton(
                        viewModel = viewModel,
                        wishlist = wishlist,
                    )
                }
            }
        }
    }
}