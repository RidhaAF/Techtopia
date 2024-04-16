package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    desc: String,
    tint: Color = LocalContentColor.current,
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = icon,
            contentDescription = desc,
            tint = tint,
        )
    }
}