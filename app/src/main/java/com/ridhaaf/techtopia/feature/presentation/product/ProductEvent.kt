package com.ridhaaf.techtopia.feature.presentation.product

sealed class ProductEvent {
    data object Refresh : ProductEvent()
}