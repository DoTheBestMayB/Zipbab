package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.remote.service.FcmService
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val fcmService: FcmService
) : NotificationRepository {
    override suspend fun registerToken(
        uuid: String,
        deviceId: String,
        pushType: String,
        pushToken: String
    ) {
        fcmService.registerToken(uuid = uuid, deviceId = deviceId, pushType = pushType, pushToken = pushToken)
    }

    override suspend fun downloadToken(uuid: String) : com.bestapp.zipbab.data.remote.notification.DownloadToken {
        return fcmService.downloadToken(uuid = uuid)
    }

    override suspend fun deleteToken(uuid: String, deviceId: String, pushType: String) {
        fcmService.deleteToken(uuid = uuid, deviceId = deviceId, pushType = pushType)
    }

    override suspend fun sendNotification(message: com.bestapp.zipbab.data.remote.notification.fcm.PushNotification, token: String) { //noti 보내기 -> meeting에서 관리
        fcmService.sendNotification(message = message, apikey = token)
    }

}