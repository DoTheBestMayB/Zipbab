package com.bestapp.zipbab.domain.model.user

data class ProfilePost(
    val uuid: String,
    val writer: String,
    val images: List<String>,
)
