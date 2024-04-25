package com.ridhaaf.techtopia.feature.presentation.home

import com.ridhaaf.techtopia.feature.data.models.banner.Banner
import com.ridhaaf.techtopia.feature.data.models.category.Category
import com.ridhaaf.techtopia.feature.data.models.product.Product

data class HomeState(
    val isBannerLoading: Boolean = false,
    val bannerSuccess: List<Banner>? = null,
    val bannerError: String = "",
    val isCategoriesLoading: Boolean = false,
    val categoriesSuccess: List<Category>? = null,
    val categoriesError: String = "",
    val isBestSellerLoading: Boolean = false,
    val bestSellerSuccess: List<Product>? = null,
    val bestSellerError: String = "",
    val isProductsLoading: Boolean = false,
    val productsSuccess: List<Product>? = null,
    val productsError: String = "",
)
