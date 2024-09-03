package com.bestapp.zipbab.ui.profileimageselect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.bestapp.zipbab.data.repository.GalleryRepository
import com.bestapp.zipbab.model.GalleryImage
import com.bestapp.zipbab.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileImageSelectViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
) : ViewModel() {

    val items: Flow<PagingData<GalleryImage>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, initialLoadSize = ITEMS_PER_PAGE * 2),
        pagingSourceFactory = { galleryRepository.galleryPagingSource() }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toUi()
        }
    }.cachedIn(viewModelScope)

    companion object {
        private const val ITEMS_PER_PAGE = 27
    }
}