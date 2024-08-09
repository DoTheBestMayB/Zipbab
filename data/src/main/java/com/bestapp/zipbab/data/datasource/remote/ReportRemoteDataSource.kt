package com.bestapp.zipbab.data.datasource.remote

interface ReportRemoteDataSource {

    suspend fun reportUser(userDocumentID: String): Boolean
    suspend fun reportPost(userDocumentID: String, postDocumentID: String): Boolean
}