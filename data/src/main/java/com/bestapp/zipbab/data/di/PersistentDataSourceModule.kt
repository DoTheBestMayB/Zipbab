package com.bestapp.zipbab.data.di

import com.bestapp.zipbab.data.datasource.remote.PostRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.PostRemoteDataSourceImpl
import com.bestapp.zipbab.data.datasource.remote.StorageRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.StorageRemoteDataSourceImpl
import com.bestapp.zipbab.data.datasource.remote.UserRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PersistentDataSourceModule {

    @Binds
    abstract fun provideStorageRemoteDataSource(storageRemoteDataSourceImpl: StorageRemoteDataSourceImpl): StorageRemoteDataSource

    @Binds
    abstract fun providePostRemoteDataSource(postRemoteDataSourceImpl: PostRemoteDataSourceImpl): PostRemoteDataSource

    @Binds
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource
}