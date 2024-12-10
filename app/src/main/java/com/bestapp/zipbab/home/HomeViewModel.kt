package com.bestapp.zipbab.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {

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

