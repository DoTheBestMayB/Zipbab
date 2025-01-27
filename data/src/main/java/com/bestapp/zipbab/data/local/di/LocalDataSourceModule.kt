package com.bestapp.zipbab.data.local.di

import com.bestapp.zipbab.data.local.datasource.CategoryLocalDataSource
import com.bestapp.zipbab.data.local.datasource.CategoryLocalDataSourceImpl
import com.bestapp.zipbab.data.local.datasource.NotificationLocalDataSource
import com.bestapp.zipbab.data.local.datasource.NotificationLocalDataSourceImpl
import com.bestapp.zipbab.data.local.datasource.UserPrivateLocalDataSource
import com.bestapp.zipbab.data.local.datasource.UserPrivateLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindsUserLocalDataSource(userPrivateLocalDataSourceImpl: UserPrivateLocalDataSourceImpl): UserPrivateLocalDataSource

    @Binds
    abstract fun bindsCategoryLocalDataSource(categoryLocalDataSourceImpl: CategoryLocalDataSourceImpl): CategoryLocalDataSource

    @Binds
    abstract fun bindsNotificationLocalDataSource(notificationLocalDataSourceImpl: NotificationLocalDataSourceImpl): NotificationLocalDataSource
}
