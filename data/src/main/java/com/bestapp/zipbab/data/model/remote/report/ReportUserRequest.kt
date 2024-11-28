package com.bestapp.zipbab.data.model.remote.report

data class ReportUserRequest(
    val reporterId: String,
    val reportedUserId: String,
    val type: ReportType = ReportType.USER,
)
