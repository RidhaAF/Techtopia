package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .aspectRatio(1f)
            .background(color = MaterialTheme.colorScheme.secondary)
    )
}