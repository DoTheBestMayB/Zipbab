package com.bestapp.zipbab.data.repository.di

import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.AppSettingRepositoryImpl
import com.bestapp.zipbab.data.repository.CategoryRepository
import com.bestapp.zipbab.data.repository.CategoryRepositoryImpl
import com.bestapp.zipbab.data.repository.GalleryRepository
import com.bestapp.zipbab.data.repository.GalleryRepositoryImpl
import com.bestapp.zipbab.data.repository.MeetingRepository
import com.bestapp.zipbab.data.repository.MeetingRepositoryImpl
import com.bestapp.zipbab.data.repository.NotificationRepository
import com.bestapp.zipbab.data.repository.NotificationRepositoryImpl
import com.bestapp.zipbab.data.repository.ProviderRepository
import com.bestapp.zipbab.data.repository.ProviderRepositoryImpl
import com.bestapp.zipbab.data.repository.ReportRepository
import com.bestapp.zipbab.data.repository.ReportRepositoryImpl
import com.bestapp.zipbab.data.repository.SearchLocationRepository
import com.bestapp.zipbab.data.repository.SearchLocationRepositoryImpl
import com.bestapp.zipbab.data.repository.UserRepository
import com.bestapp.zipbab.data.repository.UserRepositoryImpl
import com.bestapp.zipbab.data.repository.VerifyRepository
import com.bestapp.zipbab.data.repository.VerifyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ViewModelRepositoryModule {

    @Binds
    abstract fun bindsAppSettingRepository(appSettingRepositoryImpl: AppSettingRepositoryImpl): AppSettingRepository

    @Binds
    abstract fun bindsCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindsMeetingRepository(meetingRepositoryImpl: MeetingRepositoryImpl): MeetingRepository

    @Binds
    abstract fun bindsSearchLocationRepository(searchLocationRepositoryImpl: SearchLocationRepositoryImpl): SearchLocationRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsNotifyRepository(notificationRepositoryImpl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    abstract fun bindsReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

    @Binds
    abstract fun bindsProviderRepository(providerRepositoryImpl: ProviderRepositoryImpl): ProviderRepository

    @Binds
    abstract fun bindsGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    abstract fun bindsVerifyRepository(verifyRepositoryImpl: VerifyRepositoryImpl): VerifyRepository
}