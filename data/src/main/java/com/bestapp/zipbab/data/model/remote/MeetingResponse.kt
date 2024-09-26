package com.bestapp.zipbab.data.model.remote

/**
 *
 * @property meetingDocumentID 미팅 고유 ID
 * @property title 제목
 * @property titleImage 대표 사진
 * @property address 주소
 * @property longitude 위도
 * @property latitude 경도
 * @property zipCode 우편번호
 * @property date 모임 날짜
 * @property hour 모임 시
 * @property minute 모임 분
 * @property participantCount 모집 인원
 * @property description 상세 설명
 * @property category 음식 카테고리
 * @property isApprovalRequired 가입시 모임장 승인 필요 여부
 * @property verification 모임 신청 회원의 인증 상태 요구값
 * @property costValueByPerson 1인당 참여 비용 실제 값 ex. 14500, 20000
 * @property hostUserDocumentID 호스트의 userDocumentID
 * @property members  참여자 tokenId 리스트
 * @property pendingMembers 대기자 tokenId 리스트
 * @property attendanceCheck 출석 체크한 tokenId 리스트
 * @property activation 모임 활성화 유무, 끝난 상태인지 아닌지 - 검색
 */
data class MeetingResponse(
    val meetingDocumentID: String = "",
    val title: String = "",
    val titleImage: String = "",
    val address: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val zipCode: String = "",
    val date: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val participantCount: Int = 0,
    val description: String = "",
    val category: String = "",
    val isApprovalRequired: Boolean = false,
    val verification: String = "NONE",
    val costValueByPerson: Int = 0,
    val hostUserDocumentID: String = "",
    val members: List<String> = emptyList(),
    val pendingMembers: List<String> = emptyList(),
    val attendanceCheck: List<String> = emptyList(),
    val activation: Boolean = false,
)

fun MeetingResponse.isEmptyData(): Boolean = meetingDocumentID == "" &&
        title == "" &&
        titleImage == "" &&
        address == "" &&
        longitude == 0.0 &&
        latitude == 0.0 &&
        zipCode == "" &&
        date == "" &&
        hour == 0 &&
        minute == 0 &&
        participantCount == 0 &&
        description == "" &&
        category == "" && !isApprovalRequired && verification == "NONE" && costValueByPerson == 0 && hostUserDocumentID == "" && members.isEmpty() && pendingMembers.isEmpty() && attendanceCheck.isEmpty() && !activation
