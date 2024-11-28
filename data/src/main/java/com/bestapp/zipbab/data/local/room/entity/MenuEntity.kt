package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param documentId 고유 id
 * @param name 이름
 * @param description 설명
 * @param imageUrl 사진 URL
 */
@Entity
data class MenuEntity(
    @PrimaryKey val documentId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
)
