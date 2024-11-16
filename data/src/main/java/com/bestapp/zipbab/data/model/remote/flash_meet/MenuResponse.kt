package com.bestapp.zipbab.data.model.remote.flash_meet

/**
 * @property name 메뉴 이름
 * @property description 메뉴 설명
 * @property imageUrl 메뉴 사진 URL
 */
data class MenuResponse(
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
)
