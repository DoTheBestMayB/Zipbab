package com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategorySelectUiState())
    val uiState: StateFlow<CategorySelectUiState> = _uiState.asStateFlow()

//    init {
//        viewModelScope.launch {
//            val categories = categoryRepository.getFoodCategory().food.map {
//                it.toCategory()
//            }
//
//            _uiState.emit(CategorySelectUiState(categories))
//        }
//    }

    fun onCheckedStateChange(selectedChips: List<String>) {
        _uiState.value = _uiState.value.copy(
            categories = _uiState.value.categories.map {
                it.copy(
                    isSelected = it.name in selectedChips
                )
            }
        )
    }
}
