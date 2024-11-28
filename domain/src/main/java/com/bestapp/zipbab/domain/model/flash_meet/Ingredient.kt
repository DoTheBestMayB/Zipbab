package com.bestapp.zipbab.domain.model.flash_meet

data class Ingredient(
    val uuid: String,
    val name: String,
    val quantity: String,
    val discount: Number,
    val imageUrl: String,
    val remainingQuantity: Int,
)
