package com.bestapp.zipbab.domain.model.notice

/**
 * 홈 화면 상단 부에 표시할 이벤트, 공지사항 관련 데이터
 *
 * @param displayText 표시할 텍스트
 * @param eventId 이벤트, 공지사항 화면으로 이동했을 때 불러올 데이터 고유 id
 * @param type 타입 ex) 이벤트, 공지사항
 */
data class AnnouncementNotification(
    val displayText: String,
    val eventId: String,
    val type: AnnouncementNotificationType,
)
