package com.bestapp.zipbab.data.local.di

import android.content.Context
import com.bestapp.zipbab.data.local.datasource.GalleryImageFetcher
import com.bestapp.zipbab.data.local.datasource.GalleryPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
class GalleryModule {

    @Provides
    fun providesGalleryPagingSource(galleryImageFetcher: GalleryImageFetcher): GalleryPagingSource {
        return GalleryPagingSource(galleryImageFetcher)
    }

    @Provides
    fun providesGalleryImageFetcher(@ApplicationContext context: Context): GalleryImageFetcher {
        return GalleryImageFetcher(context.contentResolver)
    }
}