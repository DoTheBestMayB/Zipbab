package com.bestapp.zipbab.data.di

import com.bestapp.zipbab.data.datasource.local.UserLocalDataSource
import com.bestapp.zipbab.data.datasource.local.UserLocalDataSourceImpl
import com.bestapp.zipbab.data.datasource.remote.CategoryRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.CategoryRemoteDataSourceImpl
import com.bestapp.zipbab.data.datasource.remote.MeetingRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.MeetingRemoteDataSourceImpl
import com.bestapp.zipbab.data.datasource.remote.PostRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.PostRemoteDataSourceImpl
import com.bestapp.zipbab.data.datasource.remote.PrivacyRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.PrivacyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun providePrivacyRemoteDataSource(privacyRemoteDataSourceImpl: PrivacyRemoteDataSourceImpl): PrivacyRemoteDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun provideCategoryRemoteDataSource(categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource

    @Binds
    abstract fun provideMeetingRemoteDataSource(meetingRemoteDataSourceImpl: MeetingRemoteDataSourceImpl): MeetingRemoteDataSource

    @Binds
    abstract fun providePostRemoteDataSource(postRemoteDataSourceImpl: PostRemoteDataSourceImpl): PostRemoteDataSource
}