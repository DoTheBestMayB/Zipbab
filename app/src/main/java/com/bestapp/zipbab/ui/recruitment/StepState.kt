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
) {

    fun isCategorySelectValid(): Boolean = selectedCategories.isNotEmpty()

    fun isDetailInfoInputValid(): Boolean =
        meetingName.isNotBlank() && participantCount != -1 && cost != -1

    fun isLocationAndDateValid(): Boolean = address.isNotBlank() && zipCode.isNotBlank()
}
