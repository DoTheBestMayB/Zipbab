package com.bestapp.zipbab.ui.foodcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bestapp.zipbab.args.FilterArgs
import com.bestapp.zipbab.model.FilterUiState
import com.bestapp.zipbab.model.MeetingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FoodCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _meetingList = MutableStateFlow<List<MeetingUiState>>(emptyList())
    val meetingList: StateFlow<List<MeetingUiState>>
        get() = _meetingList

    private val _foodCategory = MutableStateFlow<List<FilterUiState.FoodUiState>>(emptyList())
    val foodCategory: StateFlow<List<FilterUiState.FoodUiState>> = _foodCategory

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

//        viewModelScope.launch {
//            val foodUiStateList =
//                categoryRepository.getFoodCategory().food.mapIndexed { index, filter ->
//                    if (filter.toUi().name == selectMenu) {
//                        selectIndex = index
//                    }
//                    filter.toUi()
//                }
//            _foodCategory.emit(foodUiStateList)
//            delay(100)
//            _scrollEvent.emit(FoodCategoryEvent.ScrollEvent)
//            appSettingRepository.userDocumentID.collect {
//                isLogin.emit(it.isNotEmpty())
//            }
//        }

    }

    fun getFoodMeeting(mainMenu: String) {
//        viewModelScope.launch {
//            runCatching {
//                meetingRepository.getFoodMeeting(mainMenu)
//            }.onSuccess {
//
//                val meetingUiStateList = it.map { meeting ->
//                    meeting.toUi()
//                }
//                _meetingList.emit(meetingUiStateList)
//            }
//        }
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
