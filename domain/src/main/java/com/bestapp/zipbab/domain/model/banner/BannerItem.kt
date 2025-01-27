package com.bestapp.zipbab.domain.model.banner

/**
 * @property contentUrl 배너를 클릭했을 때 보여줄 전체 이미지 URL
 */
data class BannerItem(
    val bannerUrl: String,
    val contentUrl: String? = null,
)
