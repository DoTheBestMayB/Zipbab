package com.bestapp.zipbab.data.local.room.entity

import kotlinx.serialization.Serializable

@Serializable
data class Icon(
    val imageUrl: String,
    val name: String,
)
