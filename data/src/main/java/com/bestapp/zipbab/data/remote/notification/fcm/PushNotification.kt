package com.bestapp.zipbab.data.remote.notification.fcm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushNotification(
    @Json(name = "message")val message: Message
)
