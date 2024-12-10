package com.bestapp.zipbab.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.domain.repository.AppSettingRepository
import com.bestapp.zipbab.domain.repository.CategoryRepository
import com.bestapp.zipbab.domain.repository.NotificationRepository
import com.bestapp.zipbab.model.FilterUiState
import com.bestapp.zipbab.ui.home.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    appSettingRepository: AppSettingRepository,
    categoryRepository: CategoryRepository,
) : ViewModel() {

    val alertUiState: StateFlow<AlertUiState> =
        appSettingRepository.userPrivateData
            .map { userPrivateData ->
                if (userPrivateData == null || userPrivateData.notifications.isEmpty()) {
                    AlertUiState.Empty
                } else {
                    AlertUiState.Exist
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = AlertUiState.Loading,
            )

    val flashMeetCategoryUiState: StateFlow<CategoryUiState> =
        categoryRepository.getFlashMeetCategory()
            .map { categoryGroup ->
                CategoryUiState.Success(categories = categoryGroup)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = CategoryUiState.Loading,
            )

    init {
        viewModelScope.launch {
            categoryRepository.fetchFlashMeetCategory()
        }
    }

//    val userLoginState: StateFlow<Boolean> = appSettingRepository.userDocumentID
//        .map { userDocumentID ->
//            userDocumentID.isNotBlank()
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = false,
//        )
//
//    init {
//        viewModelScope.launch {
//            val foodCategoryResponse = async {
//                categoryRepository.getFoodCategory().food.map { category ->
//                    category.toUi()
//                }
//            }
//            val costCategoryResponse = categoryRepository.getCostCategory().cost.map { category ->
//                category.toUi()
//            }
//            _costCategory.value = costCategoryResponse
//            _foodCategory.value = foodCategoryResponse.await()
//        }
//    }

    fun onWrite() {
//        _navDestination.value = if (userLoginState.value) {
//             NavDestination.Recruitment
//        } else {
//            NavDestination.Login
//        }
    }
}

