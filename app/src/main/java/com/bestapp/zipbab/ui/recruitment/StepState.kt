package com.bestapp.zipbab.ui.recruitment

/**
 * @property lastModifiedStep State를 변경한 Step
 * @property selectedCategories 모임에서 만들 음식 카테고리
 * @property meetingName 모임 이름
 * @property participantCount 참여 인원수
 * @property cost 1인당 비용
 * @property description 설명
 * @property address 장소
 * @property zipCode 우편번호
 * @property date 밀리세컨드로 표시한 모임 날짜
 * @property hour 모임 시
 * @property minute 모임 분
 */
data class StepState(
    val lastModifiedStep: Int = 0,
    val selectedCategories: List<String> = emptyList(),
    val meetingName: String = "",
    val participantCount: Int = -1,
    val cost: Int = -1,
    val description: String = "",
    val address: String = "",
    val zipCode: String = "",
    val date: Long = 0L,
    val hour: Int = -1,
    val minute: Int = -1,
) {

    fun isCategorySelectValid(): Boolean = selectedCategories.isNotEmpty()

    fun isDetailInfoInputValid(): Boolean =
        meetingName.isNotBlank() && participantCount != -1 && cost != -1

    fun isLocationAndDateValid(): Boolean = address.isNotBlank() && zipCode.isNotBlank() && date != 0L && hour != -1 && minute != -1
}
