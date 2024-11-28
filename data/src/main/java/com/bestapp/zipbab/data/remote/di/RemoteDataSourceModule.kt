package com.bestapp.zipbab.data.remote.di

import com.bestapp.zipbab.data.remote.datasource.CategoryRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.CategoryRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.FlashMeetingRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.FlashMeetingRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.PrivacyRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.PrivacyRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.ReportRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.ReportRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.VerifyRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.VerifyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindsPrivacyRemoteDataSource(privacyRemoteDataSourceImpl: PrivacyRemoteDataSourceImpl): PrivacyRemoteDataSource

    @Binds
    abstract fun bindsCategoryRemoteDataSource(categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource

    @Binds
    abstract fun bindsMeetingRemoteDataSource(flashMeetingRemoteDataSourceImpl: FlashMeetingRemoteDataSourceImpl): FlashMeetingRemoteDataSource

    @Binds
    abstract fun bindsReportRemoteDataSource(reportRemoteDataSourceImpl: ReportRemoteDataSourceImpl): ReportRemoteDataSource

    @Binds
    abstract fun bindsVerifyRemoteDataSource(verifyRemoteDataSourceImpl: VerifyRemoteDataSourceImpl): VerifyRemoteDataSource
}
