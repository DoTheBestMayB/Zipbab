package com.bestapp.zipbab.data.model.remote.user

/**
 * @property documentId 
 * @property writer 작성자 id
 * @property images 이미지 URL
 */
data class ProfilePostResponse(
    val documentId: String = "",
    val writer: String = "",
    val images: List<String> = emptyList(),
)
