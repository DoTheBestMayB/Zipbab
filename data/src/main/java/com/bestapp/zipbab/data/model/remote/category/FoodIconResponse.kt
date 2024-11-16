package com.bestapp.zipbab.data.model.remote.category

/**
 * @property icon 아이콘 이미지 Uri
 * @property name 이름 ex) 파스타, 전, 구이, 샌드위치
 */
data class FoodIconResponse(
    val icon: String = "",
    val name: String = "",
)
