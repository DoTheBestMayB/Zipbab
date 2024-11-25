package com.bestapp.zipbab.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bestapp.zipbab.data.local.datasource.GalleryImageFetcher
import com.bestapp.zipbab.data.local.datasource.GalleryPagingSource
import com.bestapp.zipbab.data.mapper.toGalleryImage
import com.bestapp.zipbab.domain.model.GalleryImage
import com.bestapp.zipbab.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GalleryRepositoryImpl @Inject constructor(
    private val galleryImageFetcher: GalleryImageFetcher
) : GalleryRepository {
    override fun getGalleryPagedImages(): Flow<PagingData<GalleryImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, initialLoadSize = ITEMS_PER_PAGE * 2),
            pagingSourceFactory = { GalleryPagingSource(galleryImageFetcher) }
        ).flow.map { pagingData ->
            pagingData.map { galleryImageInfo ->
                galleryImageInfo.toGalleryImage()
            }
        }
    }

    companion object {
        private const val ITEMS_PER_PAGE = 27
    }
}
