package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 개인정보를 포함한 유저 정보
 * @param email 인증된 이메일
 * @param phone 인증된 전화번호
 */
@Entity
data class UserPrivateEntity(
    @PrimaryKey val id: String,
    val pw: String,
    val email: String,
    val phone: String,
)
