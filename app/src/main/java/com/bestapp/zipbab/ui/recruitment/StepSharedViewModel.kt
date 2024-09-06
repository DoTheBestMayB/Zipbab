package com.bestapp.zipbab.ui.recruitment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 각 step별 선택한 정보를 RecruitViewModel로 전송하기 위한 ViewModel
 */
class StepSharedViewModel : ViewModel() {

    private val _stepState = MutableStateFlow(StepState())
    val stepState: StateFlow<StepState> = _stepState.asStateFlow()

    private val _requestAddressState = MutableSharedFlow<Boolean>()
    val requestAddressState: SharedFlow<Boolean> = _requestAddressState.asSharedFlow()

    fun updateCategory(selectedCategory: List<String>) {
        _stepState.value = _stepState.value.copy(
            selectedCategories = selectedCategory,
        )
    }

    fun updateName(name: String) {
        _stepState.value = _stepState.value.copy(
            meetingName = name,
        )
    }

    fun updateParticipantCount(count: Int) {
        _stepState.value = _stepState.value.copy(
            participantCount = count,
        )
    }

    fun updateCost(cost: Int) {
        _stepState.value = _stepState.value.copy(
            cost = cost,
        )
    }

    fun updateDescription(description: String) {
        _stepState.value = _stepState.value.copy(
            description = description,
        )
    }

    fun updateLocation(address: String, zipCode: String) {
        _stepState.value = _stepState.value.copy(
            address = address,
            zipCode = zipCode,
        )
    }

    fun requestAddressFinder() {
        // tryEmit은 buffer가 없으면 emit에 실패한다.
        // https://github.com/Kotlin/kotlinx.coroutines/issues/2387
//        _requestAddressState.tryEmit(true)

        viewModelScope.launch {
            _requestAddressState.emit(true)
        }
    }

    fun updateStep(step: Int) {
        _stepState.value = _stepState.value.copy(
            lastModifiedStep = step,
        )
    }

    fun updateDate(date: Long) {
        _stepState.value = _stepState.value.copy(
            date = date,
        )
    }

    fun updateTime(hour: Int, minute: Int) {
        _stepState.value = _stepState.value.copy(
            hour = hour,
            minute = minute,
        )
    }

    fun updateApprovalCondition(isApprovalRequired: Boolean) {
        _stepState.value = _stepState.value.copy(
            isApprovalRequired = isApprovalRequired,
        )
    }

}