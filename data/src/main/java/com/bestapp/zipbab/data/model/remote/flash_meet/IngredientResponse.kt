package com.bestapp.zipbab.data.model.remote.flash_meet

/**
 * @property name 재료 이름
 * @property quantity 재료 양
 * @property description 재료 설명
 * @property discount 차감 금액
 * @property imageUrl 재료 이미지 URL
 * @property remainingQuantity 남은 수량
 */
data class IngredientResponse(
    val name: String = "",
    val quantity: String = "",
    val description: String = "",
    val discount: Int = 0,
    val imageUrl: String = "",
    val remainingQuantity: Int = 0,
)
