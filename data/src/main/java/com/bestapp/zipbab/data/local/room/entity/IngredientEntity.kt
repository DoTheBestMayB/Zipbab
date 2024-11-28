package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param documentId 고유 id
 * @param name 이름
 * @param quantity 양
 * @param description 설명
 * @param discount 차감 금액
 * @param imageUrl 이미지 URL
 */
@Entity
data class IngredientEntity(
    @PrimaryKey val documentId: String,
    val name: String,
    val quantity: String,
    val description: String,
    val discount: Int,
    val imageUrl: String,
)
