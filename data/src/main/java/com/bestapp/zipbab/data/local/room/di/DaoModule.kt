package com.bestapp.zipbab.data.local.room.di

import com.bestapp.zipbab.data.local.room.dao.CategoryDao
import com.bestapp.zipbab.data.local.room.dao.UserPrivateDao
import com.bestapp.zipbab.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideUserPrivateDao(
        database: AppDatabase,
    ): UserPrivateDao = database.userPrivateDao()

    @Provides
    fun provideFlashMeetCategoryDao(
        database: AppDatabase,
    ): CategoryDao = database.flashMeetCategoryDao()
}
