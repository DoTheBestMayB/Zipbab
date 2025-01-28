package com.bestapp.zipbab.compose_ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.domain.repository.BannerRepository
import com.bestapp.zipbab.domain.repository.CategoryRepository
import com.bestapp.zipbab.domain.repository.NoticeRepository
import com.bestapp.zipbab.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    bannerRepository: BannerRepository,
    noticeRepository: NoticeRepository,
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

        viewModelScope.launch {
            val bannerResult = async {
                bannerRepository.getHomeBanner()
            }

            noticeRepository.fetchAnnouncement().onSuccess { result ->
                state = state.copy(
                    announcementText = result?.displayText ?: "",
                    announcementId = result?.eventId ?: "",
                )
            }
            bannerResult.await().onSuccess { result ->
                state = state.copy(
                    banners = result
                )
            }
        }
    }
}

