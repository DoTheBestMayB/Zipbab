package com.bestapp.zipbab.ui.recruitment.viewpager.memberverificationcondition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.UserRepository
import com.bestapp.zipbab.model.UserUiState
import com.bestapp.zipbab.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MemberVerificationConditionViewModel @Inject constructor(
    appSettingRepository: AppSettingRepository,
    private val userRepository: UserRepository,
): ViewModel() {

    val userUiState: StateFlow<UserUiState> = appSettingRepository.userDocumentID
        .map { userDocumentID ->
            if (userDocumentID.isBlank()) {
                UserUiState()
            } else {
                val user = userRepository.getUser(userDocumentID).toUi()
                user
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UserUiState(),
        )

    private val _verificationRequireState = MutableStateFlow(VerificationRequireState())
    val verificationRequireState: StateFlow<VerificationRequireState> = _verificationRequireState.asStateFlow()

    fun checkLeaderValidation(verification: Verification?) {
        val requiredVerification = when (verification) {
            Verification.NONE, null -> Verification.NONE
            Verification.EMAIL -> {
                if (userUiState.value.verifiedEmail.isNotBlank()) {
                    Verification.NONE
                } else {
                    Verification.EMAIL
                }
            }
            Verification.PHONE -> {
                if (userUiState.value.verifiedPhone.isNotBlank()) {
                    Verification.NONE
                } else {
                    Verification.PHONE
                }
            }
        }
        _verificationRequireState.value = _verificationRequireState.value.copy(
            requiredVerification = requiredVerification,
        )
    }

}