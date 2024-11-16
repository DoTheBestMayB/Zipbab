package com.bestapp.zipbab.data.model.remote.flash_meet

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * @property title 제목
 * @property status 모임 상태
 * @property mainImageUrl 대표 사진
 * @property participantCount 모집 인원
 * @property costPerPerson 1인당 참여 비용 실제 값 ex. 14500, 20000
 * @property address 주소
 * @property longitude 위도
 * @property latitude 경도
 * @property zipCode 우편번호
 * @property dateTime 날짜, 시간
 * @property description 모임 소개 문구
 * @property category 카테고리
 * @property isApprovalRequired 가입시 모임장 승인 필요 여부
 * @property verification 모임 신청 회원의 인증 상태 요구값
 * @property hostUser 호스트의 id
 * @property members 가입한 멤버 id
 * @property menu 오늘의 메뉴 리스트
 * @property ingredients 준비 재료
 */
data class FlashMeetingResponse(
    val title: String = "",
    val status: String = "",
    val mainImageUrl: String = "",
    val participantCount: Int = 0,
    val costPerPerson: Int = 0,
    val address: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val zipCode: String = "",
    val dateTime: ZonedDateTime = defaultDateTime,
    val description: String = "",
    val category: String = "",
    val isApprovalRequired: Boolean = false,
    val verification: String = "NONE",
    val hostUser: String = "",
    val members: List<JoinedMemberResponse> = emptyList(),
    val menu: List<MenuResponse> = emptyList(),
    val ingredients: List<IngredientResponse> = emptyList(),
)
