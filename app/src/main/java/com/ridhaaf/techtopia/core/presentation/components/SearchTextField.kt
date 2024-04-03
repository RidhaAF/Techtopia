package com.ridhaaf.techtopia.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.ridhaaf.techtopia.feature.presentation.search.SearchEvent
import com.ridhaaf.techtopia.feature.presentation.search.SearchViewModel

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    query: String,
    viewModel: SearchViewModel,
) {
    DefaultTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = query,
        onValueChange = { viewModel.onEvent(SearchEvent.Search(it)) },
        placeholder = "Search products...",
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
            )
        },
        trailingIcon = if (query.isNotEmpty()) {
            {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .clickable {
                            viewModel.onEvent(SearchEvent.Search(""))
                        },
                )
            }
        } else null,
    )
}