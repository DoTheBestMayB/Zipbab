package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface ReportRepository {

    suspend fun reportUser(reporterId: String, reportedUserId: String): Result<Boolean, NetworkError>
    suspend fun reportPost(reporterId: String, postDocumentId: String): Result<Boolean, NetworkError>
}
