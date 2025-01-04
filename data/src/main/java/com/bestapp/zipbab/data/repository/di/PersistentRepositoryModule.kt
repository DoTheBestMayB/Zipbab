package com.bestapp.zipbab.data.repository.di

import com.bestapp.zipbab.domain.repository.ProfilePostRepository
import com.bestapp.zipbab.data.repository.ProfilePostRepositoryImpl
import com.bestapp.zipbab.domain.repository.StorageRepository
import com.bestapp.zipbab.data.repository.StorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PersistentRepositoryModule {

    @Binds
    abstract fun bindsStorageRepository(storageRepositoryImpl: StorageRepositoryImpl): StorageRepository

    @Binds
    abstract fun bindsProfilePostRepository(profilePostRepositoryImpl: ProfilePostRepositoryImpl): ProfilePostRepository
}
