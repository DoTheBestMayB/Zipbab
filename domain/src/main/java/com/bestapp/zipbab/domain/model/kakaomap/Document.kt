package com.bestapp.zipbab.domain.model.kakaomap

data class Document(
    val address: Address?,
    val longitude: String?,
    val latitude: String?,
    val addressName: String?,
    val addressType: String?,
    val roadAddress: RoadAddress?,
)
