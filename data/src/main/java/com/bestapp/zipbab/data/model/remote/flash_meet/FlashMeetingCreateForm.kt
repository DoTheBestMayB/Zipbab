package com.bestapp.zipbab.data.model.remote.flash_meet

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

data class FlashMeetingCreateForm(
    val title: String = "",
    val searchKeywords: List<String> = listOf(),
    val status: String = "",
    val mainImageUrl: String = "",
    val participantCount: Int = 0,
    val costPerPerson: Int = 0,
    val address: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val zipCode: String = "",
    val dateTime: ZonedDateTime = defaultDateTime,
    val description: String = "",
    val category: String = "",
    val isApprovalRequired: Boolean = false,
    val verification: String = "NONE",
    val hostUser: String = "",
    val members: List<String> = emptyList(),
    val menu: List<MenuResponse> = emptyList(),
    val ingredients: List<IngredientResponse> = emptyList(),
)
