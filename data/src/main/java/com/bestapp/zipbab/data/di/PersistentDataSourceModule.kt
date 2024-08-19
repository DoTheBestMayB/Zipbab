package com.bestapp.zipbab.data.di

import com.bestapp.zipbab.data.datasource.remote.StorageRemoteDataSource
import com.bestapp.zipbab.data.datasource.remote.StorageRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PersistentDataSourceModule {

    @Binds
    abstract fun provideStorageRemoteDataSource(storageRemoteDataSourceImpl: StorageRemoteDataSourceImpl): StorageRemoteDataSource
}