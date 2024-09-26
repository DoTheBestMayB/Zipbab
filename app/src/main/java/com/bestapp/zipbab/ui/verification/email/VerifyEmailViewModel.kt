package com.bestapp.zipbab.ui.verification.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.UserRepository
import com.bestapp.zipbab.data.repository.VerifyRepository
import com.bestapp.zipbab.model.UserUiState
import com.bestapp.zipbab.model.VerifyState
import com.bestapp.zipbab.model.toUi
import com.bestapp.zipbab.ui.verification.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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

    private val userUiState: StateFlow<UserUiState> = appSettingRepository.userDocumentID
        .map { userDocumentID ->
            if (userDocumentID.isBlank()) {
                UserUiState()
            } else {
                val user = userRepository.getUser(userDocumentID).toUi()
                user
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UserUiState(),
        )

    private val _uiState = MutableStateFlow(VerifyEmailUiState())
    val uiState: StateFlow<VerifyEmailUiState> = _uiState.asStateFlow()

    private val _message = MutableSharedFlow<VerifyEmailMessage>()
    val message: SharedFlow<VerifyEmailMessage> = _message.asSharedFlow()

    private var authStateObserveJob: Job? = null

    private val _isVerified = MutableStateFlow(false)
    val isVerified: StateFlow<Boolean> = _isVerified.asStateFlow()

    fun sendCode() {
        _uiState.value = _uiState.value.copy(
            step = Step.VERIFICATION_CODE_CHECK,
        )
        viewModelScope.launch {
            val state = verifyRepository.sendCode(_uiState.value.email, userUiState.value.pw).toUi()
            when (state) {
                VerifyState.AlreadyUsedEmail -> _message.emit(VerifyEmailMessage.ALREADY_USED_EMAIL)
                VerifyState.Fail, VerifyState.FailAtSendVerificationEmail -> _message.emit(
                    VerifyEmailMessage.FAIL_SEND_CODE
                )
                VerifyState.PasswordTooShort -> _message.emit(VerifyEmailMessage.PASS_WORD_TOO_SHORT)

                VerifyState.Success -> {
                    _message.emit(VerifyEmailMessage.SUCCESS_SEND_CODE)
                    setAuthenticationObserve()
                    return@launch
                }

            }
            _uiState.value = _uiState.value.copy(
                step = Step.DEFAULT
            )
        }

    }

    private fun setAuthenticationObserve() {
        authStateObserveJob?.cancel()
        authStateObserveJob = viewModelScope.launch {
            verifyRepository.getEmailAuthState(_uiState.value.email).collect { isVerified ->
                // 이메일 인증이 완료되면 사용자 정보에 이메일 정보를 갱신한다.
                if (isVerified) {
                    val isSuccess = userRepository.updateEmail(
                        userUiState.value.userDocumentID,
                        _uiState.value.email
                    )
                    if (isSuccess) {
                        _isVerified.emit(true)
                    } else {
                        // 인증은 완료됐지만, 이메일 정보 갱신에 실패한 경우, 사용자가 화면에 다시 재진입해서 요청하도록 처리
                        _message.emit(VerifyEmailMessage.FAIL_UPDATE_AUTH_STATE)
                    }
                }
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

    fun checkVerificationStatus() {
        viewModelScope.launch {
            verifyRepository.renewAuthState(_uiState.value.email, userUiState.value.pw)
        }
    }

}