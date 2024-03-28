package com.ridhaaf.techtopia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ridhaaf.techtopia.core.presentation.components.DefaultBottomNavigation
import com.ridhaaf.techtopia.core.presentation.routes.Routes
import com.ridhaaf.techtopia.feature.presentation.home.HomeScreen
import com.ridhaaf.techtopia.ui.theme.TechtopiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechtopiaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val mainRoutes = listOf(
        Routes.HOME,
        Routes.WISHLIST,
        Routes.ORDER_LIST,
        Routes.PROFILE,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentDestination?.route in mainRoutes) {
                DefaultBottomNavigation(currentDestination, navController)
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(it)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    navController = navController,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TechtopiaTheme {
        App()
    }
}