package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.notice.AnnouncementNotificationResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface NoticeRemoteDataSource {

    suspend fun fetchAnnouncement(): Result<AnnouncementNotificationResponse?, NetworkError>
}
