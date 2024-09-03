package com.bestapp.zipbab.data.remote.datasource

interface ReportRemoteDataSource {

    suspend fun reportUser(userDocumentID: String): Boolean
    suspend fun reportPost(userDocumentID: String, postDocumentID: String): Boolean
}