package com.bestapp.zipbab.domain.model.user

import java.time.ZonedDateTime

data class ProfilePost(
    val uuid: String,
    val writer: String,
    val images: List<String>,
    val createdAt: ZonedDateTime,
)
