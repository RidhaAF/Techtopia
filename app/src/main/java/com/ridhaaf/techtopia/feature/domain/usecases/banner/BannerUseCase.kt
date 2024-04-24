package com.ridhaaf.techtopia.feature.domain.usecases.banner

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.banner.Banner
import com.ridhaaf.techtopia.feature.domain.repositories.banner.BannerRepository
import kotlinx.coroutines.flow.Flow

class BannerUseCase(private val repository: BannerRepository) {
    fun getBanners(): Flow<Resource<List<Banner>>> {
        return repository.getBanners()
    }
}