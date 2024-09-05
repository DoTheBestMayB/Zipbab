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
        _uiState.value = _uiState.value.copy(
            currentStep = (_uiState.value.currentStep + 1).coerceAtMost(RecruitmentState.MAX_STEP)
        )
    }

    fun onBefore() {
        _uiState.value = _uiState.value.copy(
            currentStep = (_uiState.value.currentStep - 1).coerceAtLeast(RecruitmentState.MIN_STEP)
        )
    }
}