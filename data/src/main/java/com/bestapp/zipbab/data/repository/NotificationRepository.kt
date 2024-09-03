package com.bestapp.zipbab.data.repository

interface NotificationRepository {

    suspend fun registerToken(uuid: String, deviceId: String, pushType: String, pushToken: String)

    suspend fun downloadToken(uuid: String) : com.bestapp.zipbab.data.remote.notification.DownloadToken

    suspend fun deleteToken(uuid: String, deviceId: String, pushType: String)

    suspend fun sendNotification(message: com.bestapp.zipbab.data.remote.notification.fcm.PushNotification, token: String)

}