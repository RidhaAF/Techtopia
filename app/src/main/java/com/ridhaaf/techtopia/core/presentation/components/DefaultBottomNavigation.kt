package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ridhaaf.techtopia.core.presentation.routes.Routes

data class BottomNavigationBar(
    val route: String = "",
    val icon: ImageVector = Icons.Rounded.Home,
    val label: String = "",
) {
    fun bottomNavigationItems(): List<BottomNavigationBar> {
        return listOf(
            BottomNavigationBar(
                route = Routes.HOME,
                icon = Icons.Rounded.Home,
                label = "Home",
            ),
            BottomNavigationBar(
                route = Routes.WISHLIST,
                icon = Icons.Rounded.Favorite,
                label = "Wishlist",
            ),
            BottomNavigationBar(
                route = Routes.ORDER_LIST,
                icon = Icons.Rounded.List,
                label = "Transaction",
            ),
            BottomNavigationBar(
                route = Routes.PROFILE,
                icon = Icons.Rounded.Person,
                label = "Me",
            ),
        )
    }
}

@Composable
fun DefaultBottomNavigation(
    currentDestination: NavDestination?,
    navController: NavController,
) {
    NavigationBar {
        BottomNavigationBar().bottomNavigationItems().forEachIndexed { _, navigationItem ->
            NavigationBarItem(
                selected = navigationItem.route == currentDestination?.route,
                onClick = {
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = navigationItem.label,
                    )
                },
                label = {
                    Text(
                        text = navigationItem.label,
                    )
                }
            )
        }
    }
}