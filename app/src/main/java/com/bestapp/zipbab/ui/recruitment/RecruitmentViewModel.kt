package com.bestapp.zipbab.ui.recruitment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecruitmentViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(RecruitmentState())
    val uiState: StateFlow<RecruitmentState> = _uiState.asStateFlow()

    fun onNext() {
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