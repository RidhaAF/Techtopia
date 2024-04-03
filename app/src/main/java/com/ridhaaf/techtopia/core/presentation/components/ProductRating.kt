package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProductRating(rating: Double, sold: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "Rating",
            modifier = Modifier.size(16.dp),
            tint = Color.Yellow,
        )
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = " • $sold sold",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}