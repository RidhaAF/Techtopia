package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.feature.presentation.home.HomeState

@Composable
fun CategoriesSection(
    state: HomeState,
    navController: NavController? = null,
) {
    val loading = state.isCategoriesLoading
    val categories = state.categoriesSuccess
    val error = state.categoriesError

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            DefaultProgressIndicator()
        }
    } else if (!categories.isNullOrEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onClick = { /*TODO*/ },
                )
            }
        }
    } else {
        DefaultErrorText(
            modifier = Modifier.padding(horizontal = 16.dp),
            message = error,
        )
    }
}