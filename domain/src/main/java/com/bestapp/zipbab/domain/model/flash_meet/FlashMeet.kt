package com.bestapp.zipbab.domain.model.flash_meet

import com.bestapp.zipbab.domain.model.user.User
import java.time.ZonedDateTime

data class FlashMeeting(
    val uuid: String,
    val title: String,
    val status: FlashMeetStatus,
    val mainImageUrl: String,
    val participantCount: Int,
    val costPerPerson: Int,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val zipCode: String,
    val dateTime: ZonedDateTime,
    val description: String,
    val category: String,
    val isApprovalRequired: Boolean,
    val verification: String,
    val hostUser: String,
    val members: List<User>,
    val menu: List<Menu>,
    val ingredients: List<Ingredient>,
)
