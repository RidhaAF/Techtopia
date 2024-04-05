package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DefaultBackButton(navController: NavController? = null) {
    ActionButton(
        onClick = { navController?.popBackStack() },
        icon = Icons.AutoMirrored.Rounded.ArrowBack,
        desc = "Back",
    )
}