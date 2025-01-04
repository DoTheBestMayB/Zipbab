package com.bestapp.zipbab.data.repository.di

import com.bestapp.zipbab.domain.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.AppSettingRepositoryImpl
import com.bestapp.zipbab.domain.repository.CategoryRepository
import com.bestapp.zipbab.data.repository.CategoryRepositoryImpl
import com.bestapp.zipbab.domain.repository.GalleryRepository
import com.bestapp.zipbab.data.repository.GalleryRepositoryImpl
import com.bestapp.zipbab.domain.repository.FlashMeetingRepository
import com.bestapp.zipbab.data.repository.FlashMeetingRepositoryImpl
import com.bestapp.zipbab.domain.repository.ReportRepository
import com.bestapp.zipbab.data.repository.ReportRepositoryImpl
import com.bestapp.zipbab.domain.repository.SearchLocationRepository
import com.bestapp.zipbab.data.repository.SearchLocationRepositoryImpl
import com.bestapp.zipbab.domain.repository.UserRepository
import com.bestapp.zipbab.data.repository.UserRepositoryImpl
import com.bestapp.zipbab.domain.repository.AuthRepository
import com.bestapp.zipbab.data.repository.AuthRepositoryImpl
import com.bestapp.zipbab.data.repository.NoticeRepositoryImpl
import com.bestapp.zipbab.data.repository.NotificationRepositoryImpl
import com.bestapp.zipbab.domain.repository.NoticeRepository
import com.bestapp.zipbab.domain.repository.NotificationRepository
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
    abstract fun bindsMeetingRepository(meetingRepositoryImpl: FlashMeetingRepositoryImpl): FlashMeetingRepository

    @Binds
    abstract fun bindsSearchLocationRepository(searchLocationRepositoryImpl: SearchLocationRepositoryImpl): SearchLocationRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

    @Binds
    abstract fun bindsGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsNotificationRepository(notificationRepositoryImpl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    abstract fun bindsNoticeRepository(noticeRepositoryImpl: NoticeRepositoryImpl): NoticeRepository
}
