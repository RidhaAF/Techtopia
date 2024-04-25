package com.ridhaaf.techtopia.feature.data.repositories.banner

import com.ridhaaf.techtopia.core.utils.Resource
import com.ridhaaf.techtopia.feature.data.models.banner.Banner
import com.ridhaaf.techtopia.feature.domain.repositories.banner.BannerRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : BannerRepository {
    override fun getBanners(): Flow<Resource<List<Banner>>> = flow {
        try {
            emit(Resource.Loading())

            val banners = fetchBannersFromApi().select().decodeList<Banner>()

            if (banners.isEmpty()) {
                emit(Resource.Error("No banners found"))
            } else {
                emit(Resource.Success(banners))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private fun fetchBannersFromApi(): PostgrestQueryBuilder {
        return supabase.from("banners")
    }
}