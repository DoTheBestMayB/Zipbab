package com.bestapp.zipbab.args

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageArgs(
    val uri: Uri = Uri.EMPTY,
    val name: String = "",
) : Parcelable
