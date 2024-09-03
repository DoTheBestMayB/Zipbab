package com.bestapp.zipbab.data.remote.notification

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PushMsgJson(
    @Json(name = "for_fcm") val forFcm: ForFcm
)