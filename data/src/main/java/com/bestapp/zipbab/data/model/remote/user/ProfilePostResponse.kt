package com.bestapp.zipbab.data.model.remote.user

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

/**
 * @property documentId 
 * @property writer 작성자 id
 * @property images 이미지 URL
 * @property createdAt 생성 시간
 */
data class ProfilePostResponse(
    val documentId: String = "",
    val writer: String = "",
    val images: List<String> = emptyList(),
    val createdAt: ZonedDateTime = defaultDateTime,
)
