package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.banner.BannerItemResponse
import com.bestapp.zipbab.domain.model.banner.BannerItem

fun BannerItemResponse.toDomain(): BannerItem {
    return BannerItem(
        bannerUrl = bannerUrl,
        contentUrl = contentUrl,
    )
}
