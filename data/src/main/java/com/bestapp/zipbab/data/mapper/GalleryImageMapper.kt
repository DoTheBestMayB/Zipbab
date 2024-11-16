package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.local.GalleryImageInfo
import com.bestapp.zipbab.domain.model.GalleryImage

fun GalleryImageInfo.toGalleryImage(): GalleryImage {
    return GalleryImage(
        uri = uri.toString(),
        name = name,
        orderId = orderId,
    )
}
