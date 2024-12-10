package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey val name: String,
    val imageUrl: String,
)
