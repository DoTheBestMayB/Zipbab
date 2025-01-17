package com.bestapp.zipbab.ui.meetupmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.model.UserUiState
import com.bestapp.zipbab.ui.meetupmap.model.MeetUpMapUi
import com.bestapp.zipbab.ui.meetupmap.model.MeetUpMapUiState
import com.bestapp.zipbab.ui.meetupmap.model.MeetingMarkerUiStates
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetUpMapViewModel @Inject constructor(
) : ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    private val _isLocationPermissionGranted = MutableSharedFlow<Boolean>(replay = 1)
    val isLocationPermissionGranted: SharedFlow<Boolean> =
        _isLocationPermissionGranted.asSharedFlow()

    private val _meetUpMapUiState = MutableStateFlow(MeetUpMapUiState())
    val meetUpMapUiState: StateFlow<MeetUpMapUiState> = _meetUpMapUiState.asStateFlow()

    private val _meetingMarkerUiStates = MutableStateFlow(
        MeetingMarkerUiStates()
    )
    val meetingMarkerUiStates: StateFlow<MeetingMarkerUiStates> = _meetingMarkerUiStates.asStateFlow()

    fun setMapReady(isReady: Boolean) {
        viewModelScope.launch {
            _meetingMarkerUiStates.value = _meetingMarkerUiStates.value.copy(
                isMapReady = isReady
            )
        }
    }

    fun setRequestPermissionResult(isLocationAllGranted: Boolean) {
        viewModelScope.launch {
            _isLocationPermissionGranted.emit(isLocationAllGranted)
        }
    }

    fun setMeetingLabels(labels: List<Marker>) {
        _meetingMarkerUiStates.value = _meetingMarkerUiStates.value.copy(
            meetingMarkers = labels
        )
    }

    fun getUserUiState() {
    }

    private suspend fun getUser() = {

    }

    fun getMeetings(latLngUser: LatLng) {
    }

    private fun updateMeetingWithLoginState() = viewModelScope.launch {
        _meetUpMapUiState.emit(
            _meetUpMapUiState.value.copy(
                meetUpMapMeetingUis = _meetUpMapUiState.value.meetUpMapMeetingUis.map { meetingUi ->
                    val isHost = userUiState.value.userDocumentID == meetingUi.hostUserDocumentID

                    meetingUi.copy(
                        isHost = isHost
                    )
                }
            )
        )
    }

    private fun MeetUpMapUi.addFormatDistance(): MeetUpMapUi {
        val distanceByUser = if (distance < CLASSIFICATION_STANDARD_VALUE) {
            DISTANCE_METER.format(distance * UNIT_CONVERSION_MAPPER)
        } else {
            DISTANCE_KILOMETER.format(distance)
        }
        return copy(distanceByUser = distanceByUser)
    }

    companion object {
        const val NO_LOGIN_USER_DEFAULT_NICKNAME = "익명의 사용자"

        // TODO : MVP 이후, ui에서 filter를 통해 선택할 수 있도록 제공
        const val DISTANCE_FILTER = 20.0 // 사용자 위치 기반 탐색 가능한 거리

        const val CLASSIFICATION_STANDARD_VALUE = 1.0
        const val UNIT_CONVERSION_MAPPER = 1000
        const val DISTANCE_METER = "%.0fm"
        const val DISTANCE_KILOMETER = "%.1fkm"

    }
}
