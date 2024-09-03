package com.bestapp.zipbab.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUiState(
    val url: String,
) : Parcelable