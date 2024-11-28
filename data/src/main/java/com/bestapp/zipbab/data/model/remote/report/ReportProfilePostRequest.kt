package com.bestapp.zipbab.data.model.remote.report

data class ReportProfilePostRequest(
    val reporterId: String,
    val postDocumentId: String,
    val type: ReportType = ReportType.PROFILE_POST,
)
