package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param writer 작성자 ID
 * @param images 이미지 url
 */
@Entity
data class ProfilePostEntity(
    @PrimaryKey val documentId: String,
    val writer: String,
    val images: List<String>,
)
