package com.ridhaaf.techtopia.feature.presentation.home

import com.ridhaaf.techtopia.feature.data.models.category.Category

data class HomeState(
    val isBannerLoading: Boolean = false,
    val bannerSuccess: Boolean = false,
    val bannerError: String = "",
    val isCategoriesLoading: Boolean = false,
    val categoriesSuccess: List<Category>? = null,
    val categoriesError: String = "",
    val isBestSellerLoading: Boolean = false,
    val bestSellerSuccess: Boolean = false,
    val bestSellerError: String = "",
    val isProductsLoading: Boolean = false,
    val productsSuccess: Boolean = false,
    val productsError: String = "",
)
