package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.ridhaaf.techtopia.core.utils.textLimit

@Composable
fun ProductName(name: String) {
    val text = textLimit(name, 20)

    Text(
        text = text,
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        maxLines = 1,
        style = MaterialTheme.typography.bodyMedium,
    )
}