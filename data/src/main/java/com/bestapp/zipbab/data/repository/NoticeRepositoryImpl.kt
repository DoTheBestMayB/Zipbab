package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.remote.datasource.NoticeRemoteDataSource
import com.bestapp.zipbab.domain.model.notice.AnnouncementNotification
import com.bestapp.zipbab.domain.repository.NoticeRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeRemoteDataSource: NoticeRemoteDataSource,
): NoticeRepository {

    override suspend fun fetchAnnouncement(): Result<AnnouncementNotification?, NetworkError> {
        return noticeRemoteDataSource.fetchAnnouncement().map {
            it?.toDomain()
        }
    }
}
