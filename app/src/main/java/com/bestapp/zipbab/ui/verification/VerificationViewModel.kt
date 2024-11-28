package com.bestapp.zipbab.ui.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.domain.repository.AppSettingRepository
import com.bestapp.zipbab.domain.repository.UserRepository
import com.bestapp.zipbab.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    appSettingRepository: AppSettingRepository,
    private val userRepository: UserRepository,
): ViewModel() {

    val userUiState: StateFlow<VerificationUiState> = appSettingRepository.userPrivateData
        .map { userDocumentID ->
//            if (userDocumentID.isBlank()) {
//                VerificationUiState()
//            } else {
//                val user = userRepository.getUser(userDocumentID).toUi()
//                VerificationUiState(
//                    email = user.verifiedEmail,
//                    phone = user.verifiedPhone,
//                )
//            }
            VerificationUiState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = VerificationUiState(),
        )

}
