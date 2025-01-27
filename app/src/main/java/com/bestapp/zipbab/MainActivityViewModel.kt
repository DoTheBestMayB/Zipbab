package com.bestapp.zipbab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.domain.model.user.UserPrivate
import com.bestapp.zipbab.domain.repository.AppSettingRepository
import com.bestapp.zipbab.domain.repository.CategoryRepository
import com.bestapp.zipbab.home.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appSettingRepository: AppSettingRepository,
    categoryRepository: CategoryRepository,
): ViewModel() {

    val userPrivateUiState: StateFlow<UserPrivateUiState> =
        appSettingRepository.userPrivateData
            .map { userPrivateData ->
                if (userPrivateData == null) {
                    UserPrivateUiState.NotLoggedIn
                } else {
                    UserPrivateUiState.LoggedIn(userPrivateData)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = UserPrivateUiState.Loading,
            )

    val categoryUiState: StateFlow<CategoryUiState> =
        categoryRepository.getCategory()
            .map { categoryGroup ->
                CategoryUiState.Success(categories = categoryGroup)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = CategoryUiState.Loading,
            )

    init {
        viewModelScope.launch {
            categoryRepository.fetchCategory()
        }
    }

}

interface UserPrivateUiState {
    data object Loading : UserPrivateUiState

    data object NotLoggedIn: UserPrivateUiState

    data class LoggedIn(
        val userPrivate: UserPrivate,
    ): UserPrivateUiState
}
