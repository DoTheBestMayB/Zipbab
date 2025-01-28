package com.bestapp.zipbab.navigation

import com.bestapp.zipbab.domain.model.user.MeetType
import kotlinx.serialization.Serializable

@Serializable
object Auth

@Serializable
object Main

@Serializable
object Setting

@Serializable
object Home

@Serializable
object Alert

@Serializable
object Search

@Serializable
data class Category(val label: String, val meetType: MeetType)

@Serializable
data class Banner(val contentUrl: String)

@Serializable
data class Announcement(val eventId: String)

@Serializable
object Recruitment

@Serializable
object SettingOverview

@Serializable
object Login

@Serializable
object Register
