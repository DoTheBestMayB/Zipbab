package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.local.datasource.GalleryPagingSource

interface GalleryRepository {

    fun galleryPagingSource(): GalleryPagingSource
}