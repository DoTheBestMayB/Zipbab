package com.bestapp.zipbab.data.repository

interface ReportRepository {

    suspend fun reportUser(userDocumentID: String): Boolean
    suspend fun reportPost(userDocumentID: String, postDocumentID: String): Boolean
}