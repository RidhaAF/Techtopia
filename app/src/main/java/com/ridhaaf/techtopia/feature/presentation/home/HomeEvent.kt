package com.ridhaaf.techtopia.feature.presentation.home

sealed class HomeEvent {
    data object Refresh : HomeEvent()
}