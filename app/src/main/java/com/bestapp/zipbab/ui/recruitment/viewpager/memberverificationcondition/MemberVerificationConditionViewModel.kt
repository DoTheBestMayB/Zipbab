package com.bestapp.zipbab.ui.recruitment.viewpager.memberverificationcondition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.model.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MemberVerificationConditionViewModel @Inject constructor(
): ViewModel() {

//    val userUiState: StateFlow<UserUiState> = appSettingRepository.userDocumentID
//        .map { userDocumentID ->
//            if (userDocumentID.isBlank()) {
//                UserUiState()
//            } else {
//                val user = userRepository.getUser(userDocumentID).toUi()
//                user
//            }
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000L),
//            initialValue = UserUiState(),
//        )

    private val _verificationRequireState = MutableStateFlow(VerificationRequireState())
    val verificationRequireState: StateFlow<VerificationRequireState> = _verificationRequireState.asStateFlow()

    fun checkLeaderValidation(verification: Verification?) {
    }
}
