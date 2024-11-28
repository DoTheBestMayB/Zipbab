package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param documentId 고유 id
 * @param title 모임 이름
 * @param mainImageUrl 모임 대표 사진 URL
 * @param participantCount 인원
 * @param costPerPerson 1인당 비용
 * @param location 장소 (예: 주소 형식)
 * @param date 날짜 (예: "2024-12-31")
 * @param time 시간 (예: "21:30")
 * @param description 모임 소개 문구
 * @param members 가입한 멤버 ID
 * @param menu 오늘의 메뉴 리스트
 * @param ingredients 준비 재료
 */
@Entity
data class FlashMeetEntity(
    @PrimaryKey val documentId: String,
    val title: String,
    val mainImageUrl: String,
    val participantCount: Int,
    val costPerPerson: Int,
    val location: String,
    val date: String,
    val time: String,
    val description: String,
    val members: List<String>,
    val menu: List<MenuEntity>,
    val ingredients: List<IngredientEntity>,
)
