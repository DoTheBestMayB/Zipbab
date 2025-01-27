package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.notice.AnnouncementNotification
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface NoticeRepository {

    suspend fun fetchAnnouncement(): Result<AnnouncementNotification?, NetworkError>
}
