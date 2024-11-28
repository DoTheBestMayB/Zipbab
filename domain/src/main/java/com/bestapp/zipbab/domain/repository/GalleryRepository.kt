package com.bestapp.zipbab.domain.repository

import androidx.paging.PagingData
import com.bestapp.zipbab.domain.model.GalleryImage
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    fun getGalleryPagedImages(): Flow<PagingData<GalleryImage>>
}
