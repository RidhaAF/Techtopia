package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    icon: ImageVector,
    desc: String,
    tint: Color = LocalContentColor.current,
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = desc,
            tint = tint,
        )
    }
}