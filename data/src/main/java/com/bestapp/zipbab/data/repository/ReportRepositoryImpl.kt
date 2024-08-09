package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.datasource.remote.ReportRemoteDataSource
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportRemoteDataSource: ReportRemoteDataSource,
) : ReportRepository {
    override suspend fun reportUser(userDocumentID: String): Boolean {
        return reportRemoteDataSource.reportUser(userDocumentID)
    }

    override suspend fun reportPost(userDocumentID: String, postDocumentID: String): Boolean {
        return reportRemoteDataSource.reportPost(userDocumentID, postDocumentID)
    }
}