package com.bestapp.zipbab.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        categoryRepository.getCategory()
            .onEach { categories ->
                state = state.copy(
                    categories = categories,
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {

    }

    fun onWrite() {
//        _navDestination.value = if (userLoginState.value) {
//             NavDestination.Recruitment
//        } else {
//            NavDestination.Login
//        }
    }
}

