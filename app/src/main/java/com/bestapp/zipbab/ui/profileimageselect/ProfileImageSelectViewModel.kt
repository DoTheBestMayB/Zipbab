package com.bestapp.zipbab.ui.profileimageselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.bestapp.zipbab.model.GalleryImageUi
import com.bestapp.zipbab.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileImageSelectViewModel @Inject constructor(
    galleryRepository: com.bestapp.zipbab.domain.repository.GalleryRepository,
) : ViewModel() {

    val items: Flow<PagingData<GalleryImageUi>> =
        galleryRepository.getGalleryPagedImages().map { pagingData ->
            pagingData.map {
                it.toUi()
            }
        }.cachedIn(viewModelScope)
}
