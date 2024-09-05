package com.bestapp.zipbab.data.remote.notification

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterToken(
    @Json(name = "NONE") val none: Int
)