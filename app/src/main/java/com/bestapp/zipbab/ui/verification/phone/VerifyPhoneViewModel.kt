package com.bestapp.zipbab.ui.verification.phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyPhoneViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyPhoneUiState())
    val uiState: StateFlow<VerifyPhoneUiState> = _uiState.asStateFlow()

    private var timeoutJob: Job? = null

    fun onCodeSent() {
        _uiState.value = _uiState.value.copy(
            step = Step.VERIFICATION_CODE_CHECK,
        )
        timeoutJob = viewModelScope.launch {
            var startTime = PHONE_AUTH_TIMEOUT

            while (startTime > 0) {
                _uiState.emit(
                    _uiState.value.copy(
                        remainVerifyTime = startTime--.toInt()
                    )
                )
                delay(1000L)
            }
        }
    }

    fun onVerified() {
        timeoutJob?.cancel()
        timeoutJob = null

        _uiState.value = _uiState.value.copy(
            step = Step.VERIFICATION_CONFIRMED,
            remainVerifyTime = 0,
        )
    }

    fun onPhoneChange(phone: String) {
        _uiState.value = _uiState.value.copy(
            phone = phone,
        )
    }

    fun sendVerificationCode() {
        TODO("Not yet implemented")
    }
}
