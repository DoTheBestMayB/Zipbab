package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface ReportRemoteDataSource {

    suspend fun reportUser(reporterId: String, reportedUserId: String): Result<Boolean, NetworkError>
    suspend fun reportProfilePost(reporterId: String, postDocumentId: String): Result<Boolean, NetworkError>
}
