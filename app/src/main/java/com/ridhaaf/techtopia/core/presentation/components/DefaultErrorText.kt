package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DefaultErrorText(modifier: Modifier = Modifier, message: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = message,
        textAlign = TextAlign.Center,
    )
}