package com.bestapp.zipbab.data.model.remote.category

/**
 * @property imageUrl 아이콘 이미지 Url
 * @property name 이름 ex) 파스타, 전, 구이, 샌드위치
 */
data class IconResponse(
    val imageUrl: String = "",
    val name: String = "",
)
