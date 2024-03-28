package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .aspectRatio(16f / 9f)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
    )
}