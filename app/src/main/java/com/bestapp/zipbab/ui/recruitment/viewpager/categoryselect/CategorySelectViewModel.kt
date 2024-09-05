package com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.data.repository.CategoryRepository
import com.bestapp.zipbab.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategorySelectUiState())
    val uiState: StateFlow<CategorySelectUiState> = _uiState.asStateFlow()

    private var selectedChips = listOf<String>()

    init {
        viewModelScope.launch {
            val categories = categoryRepository.getFoodCategory().food.map {
                it.toUi()
            }

            _uiState.emit(CategorySelectUiState(categories))
        }
    }

    fun onCheckedStateChange(selectedChips: List<String>) {
        this.selectedChips = selectedChips
    }
}