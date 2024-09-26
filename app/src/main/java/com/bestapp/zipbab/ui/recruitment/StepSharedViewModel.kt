package com.bestapp.zipbab.ui.recruitment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.data.model.remote.MeetingCreationInfo
import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.MeetingRepository
import com.bestapp.zipbab.data.repository.SearchLocationRepository
import com.bestapp.zipbab.model.kakaoLocation.toUiState
import com.bestapp.zipbab.ui.recruitment.viewpager.memberverificationcondition.Verification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 각 step별 선택한 정보를 RecruitViewModel로 전송하기 위한 ViewModel
 */
@HiltViewModel
class StepSharedViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository,
    private val appSettingRepository: AppSettingRepository,
    private val searchLocationRepository: SearchLocationRepository,
) : ViewModel() {

    private val _stepState = MutableStateFlow(StepState())
    val stepState: StateFlow<StepState> = _stepState.asStateFlow()

    private val _requestAction = MutableSharedFlow<ActionType>()
    val requestAction: SharedFlow<ActionType> = _requestAction.asSharedFlow()

    private val _message = MutableSharedFlow<Message>()
    val message: SharedFlow<Message> = _message.asSharedFlow()

    fun updateCategory(selectedCategory: List<String>) {
        _stepState.update { currentState ->
            currentState.copy(
                selectedCategories = selectedCategory,
            )
        }
    }

    fun updateName(name: String) {
        _stepState.update { currentState ->
            currentState.copy(
                meetingName = name,
            )
        }
    }

    fun updateParticipantCount(count: Int) {
        _stepState.update { currentState ->
            currentState.copy(
                participantCount = count,
            )
        }
    }

    fun updateCost(cost: Int) {
        _stepState.update { currentState ->
            currentState.copy(
                cost = cost,
            )
        }
    }

    fun updateDescription(description: String) {
        _stepState.update { currentState ->
            currentState.copy(
                description = description,
            )
        }
    }

    fun updateLocation(address: String, zipCode: String) {
        viewModelScope.launch {
            val searchLocation = searchLocationRepository.convertLocation(address).toUiState()
            val (latitude, longitude) = if (searchLocation.documentUiState.isEmpty()) {
                0.0 to 0.0
            } else {
                searchLocation.documentUiState.first().let {
                    it.latitude.toDouble() to it.longitude.toDouble()
                }
            }
            _stepState.update { currentState ->
                currentState.copy(
                    address = address,
                    longitude = longitude,
                    latitude = latitude,
                    zipCode = zipCode,
                )
            }
        }
    }

    fun requestAddressFinder() {
        // tryEmit은 buffer가 없으면 emit에 실패한다.
        // https://github.com/Kotlin/kotlinx.coroutines/issues/2387
//        _requestAddressState.tryEmit(true)

        viewModelScope.launch {
            _requestAction.emit(ActionType.ADDRESS)
        }
    }

    fun updateStep(step: Int) {
        _stepState.update { currentState ->
            currentState.copy(
                lastModifiedStep = step,
            )
        }
    }

    fun updateDate(date: String) {
        _stepState.update { currentState ->
            currentState.copy(
                date = date,
            )
        }
    }

    fun updateTime(hour: Int, minute: Int) {
        _stepState.update { currentState ->
            currentState.copy(
                hour = hour,
                minute = minute,
            )
        }
    }

    fun updateApprovalCondition(isApprovalRequired: Boolean) {
        _stepState.update { currentState ->
            currentState.copy(
                isApprovalRequired = isApprovalRequired,
            )
        }
    }

    fun updateVerification(verification: Verification) {
        _stepState.update { currentState ->
            currentState.copy(
                verification = verification,
            )
        }
    }

    fun updateLeaderVerification(isVerificationCompleted: Boolean) {
        _stepState.update { currentState ->
            currentState.copy(
                isLeaderVerificationCompleted = isVerificationCompleted,
            )
        }
    }

    fun requestVerification() {
        viewModelScope.launch {
            _requestAction.emit(ActionType.VERIFICATION)
        }
    }

    fun requestProfileImage() {
        viewModelScope.launch {
            _requestAction.emit(ActionType.PROFILE_IMAGE)
        }
    }

    fun updateProfileImage(uri: String) {
        _stepState.value = _stepState.value.copy(
            profileUri = uri,
        )
    }

    fun createMeeting() {
        viewModelScope.launch {
            val hostDocumentID = appSettingRepository.userDocumentID.firstOrNull() ?: run {
                _message.emit(Message.LOGIN_REQUIRED)
                return@launch
            }

            val meetingCreationInfo = with(_stepState.value) {
                MeetingCreationInfo(
                    hostDocumentID = hostDocumentID,
                    category = selectedCategories.first(),
                    meetingName = meetingName,
                    participantCount = participantCount,
                    cost = cost,
                    description = description,
                    address = address,
                    longitude = longitude,
                    latitude = latitude,
                    zipCode = zipCode,
                    date = date,
                    hour = hour,
                    minute = minute,
                    isApprovalRequired = isApprovalRequired ?: false,
                    verification = verification?.name ?: Verification.NONE.name,
                    profileUri = profileUri,
                )
            }
            val isSuccess = meetingRepository.createMeeting(meetingCreationInfo)

            val message = if (isSuccess) {
                Message.CREATION_COMPLETE
            } else {
                Message.CREATION_FAIL
            }
            _message.emit(message)
        }
    }

    fun onComplete() {
        viewModelScope.launch {
            _requestAction.emit(ActionType.DONE)
        }
    }

}
