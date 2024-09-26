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

class RecruitmentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecruitmentState())
    val uiState: StateFlow<RecruitmentState> = _uiState.asStateFlow()

    private val _createMeetingTrigger = MutableSharedFlow<Unit>()
    val createMeetingTrigger: SharedFlow<Unit> = _createMeetingTrigger.asSharedFlow()

    fun onNext() {
        // 마지막 Step에서 모임 생성하기 버튼을 누른 경우
        if (_uiState.value.currentStep == RecruitmentState.MAX_STEP) {
            viewModelScope.launch {
                _createMeetingTrigger.emit(Unit)
            }
            return
        }

        val newStep = (_uiState.value.currentStep + 1).coerceAtMost(RecruitmentState.MAX_STEP)

        _uiState.value = _uiState.value.copy(
            currentStep = newStep,
            steps = _uiState.value.steps.toMutableList().apply {
                this[newStep] = this[newStep].copy(isProcessed = true)
            }.toList()
        )
    }

    fun onBefore() {
        val newStep = (_uiState.value.currentStep - 1).coerceAtLeast(RecruitmentState.MIN_STEP)

        _uiState.value = _uiState.value.copy(
            currentStep = newStep,
            steps = _uiState.value.steps.toMutableList().apply {
                this[newStep + 1] = this[newStep + 1].copy(isProcessed = false)
            }.toList()
        )
    }
}