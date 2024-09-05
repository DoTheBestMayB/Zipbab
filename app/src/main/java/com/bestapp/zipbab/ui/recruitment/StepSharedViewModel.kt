package com.bestapp.zipbab.ui.recruitment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 각 step별 선택한 정보를 RecruitViewModel로 전송하기 위한 ViewModel
 */
class StepSharedViewModel : ViewModel() {

    private val _stepState = MutableStateFlow(StepState())
    val stepState: StateFlow<StepState> = _stepState.asStateFlow()

    fun updateCategory(selectedCategory: List<String>, step: Int) {
        _stepState.value = _stepState.value.copy(
            lastModifiedStep = step,
            selectedCategories = selectedCategory,
        )
    }

    fun updateName(name: String, step: Int) {
        _stepState.value = _stepState.value.copy(
            lastModifiedStep = step,
            meetingName = name,
        )
    }

    fun updateParticipantCount(count: Int, step: Int) {
        _stepState.value = _stepState.value.copy(
            lastModifiedStep = step,
            participantCount = count,
        )
    }

    fun updateCost(cost: Int, step: Int) {
        _stepState.value = _stepState.value.copy(
            lastModifiedStep = step,
            cost = cost,
        )
    }

    fun updateDescription(description: String, step: Int) {
        _stepState.value = _stepState.value.copy(
            lastModifiedStep = step,
            description = description,
        )
    }

}