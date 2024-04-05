package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ProductQuantityIcon(
    imageVector: ImageVector,
    desc: String,
    onClick: () -> Unit,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = desc,
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .clickable { onClick() },
    )
}