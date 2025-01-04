package com.bestapp.zipbab.data.remote.di

import com.bestapp.zipbab.data.remote.datasource.CategoryRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.CategoryRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.FlashMeetingRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.FlashMeetingRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.PrivacyRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.PrivacyRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.ReportRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.ReportRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.AuthRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.AuthRemoteDataSourceImpl
import com.bestapp.zipbab.data.remote.datasource.NoticeRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.NoticeRemoteDataSourceImpl
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
    abstract fun bindsFlashMeetingRemoteDataSource(flashMeetingRemoteDataSourceImpl: FlashMeetingRemoteDataSourceImpl): FlashMeetingRemoteDataSource

    @Binds
    abstract fun bindsReportRemoteDataSource(reportRemoteDataSourceImpl: ReportRemoteDataSourceImpl): ReportRemoteDataSource

    @Binds
    abstract fun bindsAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun bindsNoticeRemoteDataSource(noticeRemoteDataSourceImpl: NoticeRemoteDataSourceImpl): NoticeRemoteDataSource
}
