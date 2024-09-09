package com.bestapp.zipbab.ui.verification.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.UserRepository
import com.bestapp.zipbab.data.repository.VerifyRepository
import com.bestapp.zipbab.ui.verification.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    appSettingRepository: AppSettingRepository,
    private val userRepository: UserRepository,
    private val verifyRepository: VerifyRepository,
    private val inputValidator: InputValidator,
) : ViewModel() {

    private val userDocumentID: StateFlow<String> = appSettingRepository.userDocumentID
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "",
        )

    private val _uiState = MutableStateFlow(VerifyEmailUiState())
    val uiState: StateFlow<VerifyEmailUiState> = _uiState.asStateFlow()

    private val _message = MutableSharedFlow<VerifyEmailMessage>()
    val message: SharedFlow<VerifyEmailMessage> = _message.asSharedFlow()

    val authState: StateFlow<Boolean> = verifyRepository.getEmailAuthState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false,
        )

    fun sendCode() {
        _uiState.value = _uiState.value.copy(
            step = Step.VERIFICATION_CODE_CHECK,
        )
        viewModelScope.launch {
            val isSendDone = verifyRepository.sendCode(_uiState.value.email)
            if (isSendDone) {
                _message.emit(VerifyEmailMessage.SUCCESS_SEND_CODE)


            } else {
                _uiState.value = _uiState.value.copy(
                    step = Step.DEFAULT
                )
                _message.emit(VerifyEmailMessage.FAIL_SEND_CODE)
            }
        }

    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            isAddressValid = inputValidator.isEmailValid(email),
            email = email,
            step = Step.EMAIL_CHECK,
        )
    }

}