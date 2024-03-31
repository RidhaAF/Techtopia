package com.ridhaaf.techtopia.core.utils

import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.routes.Routes

fun redirectFromAuth(navController: NavController?) {
    navController?.navigate(Routes.HOME) {
        popUpTo(Routes.SIGN_IN) {
            inclusive = true
        }
    }
}