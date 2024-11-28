package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.remote.datasource.ReportRemoteDataSource
import com.bestapp.zipbab.domain.repository.ReportRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import javax.inject.Inject

internal class ReportRepositoryImpl @Inject constructor(
    private val reportRemoteDataSource: ReportRemoteDataSource,
) : ReportRepository {
    override suspend fun reportUser(reporterId: String, reportedUserId: String): Result<Boolean, NetworkError> {
        return reportRemoteDataSource.reportUser(reporterId, reportedUserId)
    }

    override suspend fun reportPost(
        reporterId: String,
        postDocumentId: String
    ): Result<Boolean, NetworkError> {
        return reportRemoteDataSource.reportProfilePost(reporterId, postDocumentId)
    }
}
