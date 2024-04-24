package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ridhaaf.techtopia.feature.data.models.banner.Banner

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerProducts(images: List<Banner>) {
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { images.size },
    )

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f),
            contentPadding = PaddingValues(16.dp),
            pageSpacing = 8.dp,
        ) { i ->
            val image = images[i]

            AsyncImage(
                model = image.imageUrl,
                contentDescription = "Banner Image ${i + 1}",
                modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}