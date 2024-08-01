package com.bestapp.zipbab.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.data.repository.AppSettingRepository
import com.bestapp.zipbab.data.repository.CategoryRepository
import com.bestapp.zipbab.model.FilterUiState
import com.bestapp.zipbab.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _navDestination = MutableStateFlow<NavDestination>(NavDestination.Default)
    val navDestination: StateFlow<NavDestination> = _navDestination.asStateFlow()

    private val _foodCategory = MutableStateFlow<List<FilterUiState.FoodUiState>>(emptyList())
    val foodCategory: StateFlow<List<FilterUiState.FoodUiState>> = _foodCategory.asStateFlow()

    private val _costCategory = MutableStateFlow<List<FilterUiState.CostUiState>>(emptyList())
    val costCategory: StateFlow<List<FilterUiState.CostUiState>> = _costCategory.asStateFlow()

    val userLoginState: StateFlow<Boolean> = appSettingRepository.userDocumentID
        .map { userDocumentID ->
            userDocumentID.isNotBlank()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    init {
        viewModelScope.launch {
            val foodCategoryResponse = async {
                categoryRepository.getFoodCategory().food.map { category ->
                    category.toUiState()
                }
            }
            val costCategoryResponse = categoryRepository.getCostCategory().cost.map { category ->
                category.toUiState()
            }
            _costCategory.value = costCategoryResponse
            _foodCategory.value = foodCategoryResponse.await()
        }
    }

    fun onWrite() {
        _navDestination.value = if (userLoginState.value) {
             NavDestination.Recruitment
        } else {
            NavDestination.Login
        }
    }

    fun onNavConsumed() {
        _navDestination.value = NavDestination.Default
    }
}