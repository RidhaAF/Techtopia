package com.ridhaaf.techtopia.feature.domain.repositories.banner

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.banner.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    fun getBanners(): Flow<Resource<List<Banner>>>
}