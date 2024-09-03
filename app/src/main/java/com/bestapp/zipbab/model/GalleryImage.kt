package com.bestapp.zipbab.model

import android.net.Uri
import com.bestapp.zipbab.args.ImageArgs

data class GalleryImage(
    val uri: Uri = Uri.EMPTY,
    val name: String = "",
    val orderId: Int = 0,
) {
    fun toArgs() = ImageArgs(
        uri = uri,
        name = name,
    )
}
