package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ridhaaf.techtopia.feature.presentation.home.HomeState

@Composable
fun BannerSection(
    state: HomeState,
) {
    val loading = state.isBannerLoading
    val banners = state.bannerSuccess
    val error = state.bannerError

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            DefaultProgressIndicator()
        }
    } else if (!banners.isNullOrEmpty()) {
        BannerProducts(images = banners)
    } else {
        DefaultErrorText(
            modifier = Modifier.padding(horizontal = 16.dp),
            message = error,
        )
    }
}