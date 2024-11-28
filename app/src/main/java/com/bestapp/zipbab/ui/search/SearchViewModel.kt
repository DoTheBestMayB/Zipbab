package com.bestapp.zipbab.ui.search

import androidx.lifecycle.ViewModel
import com.bestapp.zipbab.model.MeetingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {

    private val _searchMeeting = MutableStateFlow<List<MeetingUiState>>(emptyList())
    val searchMeeting: StateFlow<List<MeetingUiState>> = _searchMeeting.asStateFlow()

    fun requestSearch(query: String) {
//        viewModelScope.launch {
//            runCatching {
//                meetingRepository.getSearch(query)
//            }.onSuccess {
//                val meetingUiStateList = it.map { meeting ->
//                    meeting.toUi()
//                }
//                _searchMeeting.value = meetingUiStateList
//            }
//        }
    }
}
