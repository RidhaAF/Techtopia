package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.routes.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    navController: NavController? = null,
    navigationIcon: @Composable () -> Unit = {},
    isActionsEnabled: Boolean = true,
    actions: @Composable () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            Row {
                if (showBackButton) {
                    DefaultBackButton(navController)
                }
                navigationIcon()
            }

        },
        actions = {
            if (isActionsEnabled) {
                ActionButton(
                    onClick = { navController?.navigate(Routes.SEARCH) },
                    icon = Icons.Rounded.Search,
                    desc = "Search",
                )
                ActionButton(
                    onClick = { /*TODO*/ },
                    icon = Icons.Rounded.ShoppingCart,
                    desc = "Cart",
                )
            }
            actions()
        },
        colors = colors,
    )
}