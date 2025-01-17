package com.bestapp.zipbab.args

import android.os.Parcelable
import com.bestapp.zipbab.data.model.remote.PlaceLocation
import kotlinx.parcelize.Parcelize

@Parcelize
data class MeetingArgs(
    val meetingDocumentID: String,
    val title: String,
    val titleImage: String,
    val address: String,
    val zipCode: String,
    val date: String,
    val hour: Int,
    val minute: Int,
    val participantCount: Int,
    val description: String,
    val mainMenu: String,
    val costValueByPerson: Int,
    val hostUserDocumentID: String,
    val members: List<String>,
    val pendingMembers: List<String>,
    val attendanceCheck: List<String>,
    val activation: Boolean,
) : Parcelable

fun PlaceLocation.toUi() = PlaceLocationArgs(
    locationAddress = locationAddress,
    locationLat = locationLat,
    locationLong = locationLong
)
