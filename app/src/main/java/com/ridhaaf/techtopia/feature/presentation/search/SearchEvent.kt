package com.ridhaaf.techtopia.feature.presentation.search

sealed class SearchEvent {
    data class Search(val query: String) : SearchEvent()
}