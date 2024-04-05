package com.ridhaaf.techtopia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ridhaaf.techtopia.core.presentation.components.DefaultBottomNavigation
import com.ridhaaf.techtopia.core.presentation.routes.Routes
import com.ridhaaf.techtopia.feature.presentation.auth.sign_in.SignInScreen
import com.ridhaaf.techtopia.feature.presentation.auth.sign_up.SignUpScreen
import com.ridhaaf.techtopia.feature.presentation.home.HomeScreen
import com.ridhaaf.techtopia.feature.presentation.product.ProductScreen
import com.ridhaaf.techtopia.feature.presentation.product.product_detail.ProductDetailScreen
import com.ridhaaf.techtopia.feature.presentation.profile.ProfileScreen
import com.ridhaaf.techtopia.feature.presentation.search.SearchScreen
import com.ridhaaf.techtopia.feature.presentation.splash.SplashViewModel
import com.ridhaaf.techtopia.ui.theme.TechtopiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        actionBar?.hide()
        setContent {
            TechtopiaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App(viewModel)
                }
            }
        }
    }
}

@Composable
fun App(viewModel: SplashViewModel? = null) {
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
            startDestination = viewModel?.initialRoute() ?: Routes.SIGN_IN,
            modifier = Modifier.padding(it)
        ) {
            composable(Routes.SIGN_IN) {
                SignInScreen(
                    navController = navController,
                )
            }
            composable(Routes.SIGN_UP) {
                SignUpScreen(
                    navController = navController,
                )
            }
            composable(Routes.HOME) {
                HomeScreen(
                    navController = navController,
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    navController = navController,
                )
            }
            composable("${Routes.PRODUCTS}?type={type}&categoryId={categoryId}&categoryName={categoryName}") { navBackStack ->
                val type = navBackStack.arguments?.getString("type")
                val categoryId = navBackStack.arguments?.getString("categoryId")
                val categoryName = navBackStack.arguments?.getString("categoryName")

                ProductScreen(
                    navController = navController,
                    type = type ?: "all",
                    categoryId = categoryId ?: "0",
                    categoryName = categoryName ?: "Products",
                )
            }
            composable("${Routes.PRODUCT}/{id}") { navBackStack ->
                val id = navBackStack.arguments?.getString("id")

                ProductDetailScreen(
                    navController = navController,
                    id = id ?: "0",
                )
            }
            composable(Routes.SEARCH) {
                SearchScreen(
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