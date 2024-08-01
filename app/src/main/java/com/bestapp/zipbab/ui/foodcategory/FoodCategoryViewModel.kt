package com.bestapp.zipbab.ui.foodcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.args.FilterArgs
import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.CategoryRepository
import com.bestapp.zipbab.data.repository.MeetingRepository
import com.bestapp.zipbab.model.FilterUiState
import com.bestapp.zipbab.model.MeetingUiState
import com.bestapp.zipbab.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodCategoryViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository,
    private val categoryRepository: CategoryRepository,
    private val appSettingRepository: AppSettingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _meetingList = MutableStateFlow<List<MeetingUiState>>(emptyList())
    val meetingList: StateFlow<List<MeetingUiState>>
        get() = _meetingList

    private val _foodCategory = MutableStateFlow<List<FilterUiState.FoodUiState>>(emptyList())
    val foodCategory: StateFlow<List<FilterUiState.FoodUiState>> = _foodCategory

    private val _goMeetingNavi = MutableSharedFlow<Pair<MoveMeetingNavi, String>>(replay = 0)
    val goMeetingNavi: SharedFlow<Pair<MoveMeetingNavi, String>>
        get() = _goMeetingNavi

    private val _scrollEvent = MutableSharedFlow<FoodCategoryEvent>(replay = 0)
    val scrollEvent: SharedFlow<FoodCategoryEvent>
        get() = _scrollEvent

    private val isLogin = MutableStateFlow(false)

    private var selectMenu = ""
    private var selectIndex = DEFAULT_INDEX

    init {
        savedStateHandle.get<FilterArgs.FoodArgs>(SAVED_STATE_HANDLE_KEY)?.let {
            selectMenu = it.name
            getFoodMeeting(selectMenu)
        }

        viewModelScope.launch {
            val foodUiStateList =
                categoryRepository.getFoodCategory().food.mapIndexed { index, filter ->
                    if (filter.toUiState().name == selectMenu) {
                        selectIndex = index
                    }
                    filter.toUiState()
                }
            _foodCategory.emit(foodUiStateList)
            delay(100)
            _scrollEvent.emit(FoodCategoryEvent.ScrollEvent)
            appSettingRepository.userDocumentID.collect {
                isLogin.emit(it.isNotEmpty())
            }
        }

    }

    fun getFoodMeeting(mainMenu: String) {
        viewModelScope.launch {
            runCatching {
                meetingRepository.getFoodMeeting(mainMenu)
            }.onSuccess {

                val meetingUiStateList = it.map { meeting ->
                    meeting.toUiState()
                }
                _meetingList.emit(meetingUiStateList)
            }
        }
    }

    fun goMeeting(meetingUiState: MeetingUiState) {
        viewModelScope.launch {
            appSettingRepository.userDocumentID.collect { userDocumentID ->
                val destination = when {
                    !isLogin.value -> MoveMeetingNavi.GO_MEETING_INFO
                    userDocumentID == meetingUiState.hostUserDocumentID -> MoveMeetingNavi.GO_MEETING_MANAGEMENT
                    else -> MoveMeetingNavi.GO_MEETING_INFO
                }
                _goMeetingNavi.emit(Pair(destination, meetingUiState.meetingDocumentID))
            }
        }
    }

    fun setSelectIndex(index: Int) {
        selectIndex = index
    }

    fun getSelectIndex(): Int {
        return selectIndex
    }

    companion object {
        private const val DEFAULT_INDEX = 0
        private const val SAVED_STATE_HANDLE_KEY = "foodCategory"
    }
}