package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.ridhaaf.techtopia.core.utils.textLimit

@Composable
fun ProductName(
    name: String,
    modifier: Modifier = Modifier,
    isLimited: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val text = if (isLimited) textLimit(name, maxLines) else name

    Text(
        text = text,
        modifier = modifier,
        overflow = if (isLimited) overflow else TextOverflow.Clip,
        maxLines = if (isLimited) maxLines else Int.MAX_VALUE,
        style = style,
    )
}