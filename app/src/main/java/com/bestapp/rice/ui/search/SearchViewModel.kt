package com.bestapp.rice.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.rice.data.repository.AppSettingRepository
import com.bestapp.rice.data.repository.MeetingRepository
import com.bestapp.rice.model.MeetingUiState
import com.bestapp.rice.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val meetingRepositoryImpl: MeetingRepository,
    private val appSettingRepositoryImpl: AppSettingRepository
) : ViewModel() {

    private val _searchMeeting = MutableStateFlow<List<MeetingUiState>>(emptyList())
    val searchMeeting: StateFlow<List<MeetingUiState>>
        get() = _searchMeeting

    private val _goDirection = MutableSharedFlow<Pair<MoveDirection, String>>()
    val goDirection: SharedFlow<Pair<MoveDirection, String>>
        get() = _goDirection

    fun requestSearch(query: String) {
        viewModelScope.launch {
            runCatching {
                meetingRepositoryImpl.getSearch(query)
            }.onSuccess {
                val meetingUiStateList = it.map { meeting ->
                    meeting.toUiState()
                }
                _searchMeeting.value = meetingUiStateList
            }
        }
    }

    fun goDetailMeeting(meetingUiState: MeetingUiState) {
        viewModelScope.launch {
            appSettingRepositoryImpl.userPreferencesFlow.collect{
                if(it.isEmpty()){
                    _goDirection.emit(Pair(MoveDirection.GO_LOGIN, meetingUiState.meetingDocumentID))
                }else if(it == meetingUiState.host){
                    _goDirection.emit(Pair(MoveDirection.GO_MEETING_MANAGEMENT, meetingUiState.meetingDocumentID))
                }else {
                    _goDirection.emit(Pair(MoveDirection.GO_MEETING_INFO, meetingUiState.meetingDocumentID))
                }
            }
        }
    }
}