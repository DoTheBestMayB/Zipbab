package com.bestapp.zipbab.ui.recruitment

/**
 * @property lastModifiedStep State를 변경한 Step
 * @property selectedCategories 모임에서 만들 음식 카테고리
 */
data class StepState(
    val lastModifiedStep: Int = 0,
    val selectedCategories: List<String> = emptyList(),
)
