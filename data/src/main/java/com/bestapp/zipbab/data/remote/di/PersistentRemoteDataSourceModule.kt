package com.bestapp.zipbab.data.remote.di

import com.bestapp.zipbab.data.remote.datasource.ProfilePostRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.ProfilePostRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.StorageRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.StorageRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.UserPrivateRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserPrivateRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PersistentRemoteDataSourceModule {

    @Binds
    abstract fun bindsStorageRemoteDataSource(storageRemoteDataSourceImpl: StorageRemoteDataSourceImpl): StorageRemoteDataSource

    @Binds
    abstract fun bindsPostRemoteDataSource(postRemoteDataSourceImpl: ProfilePostRemoteDataSourceImpl): ProfilePostRemoteDataSource

    @Binds
    abstract fun bindsUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun bindsUserPrivateRemoteDataSource(userPrivateRemoteDataSourceImpl: UserPrivateRemoteDataSourceImpl): UserPrivateRemoteDataSource
}
