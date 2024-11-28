package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.report.ReportProfilePostRequest
import com.bestapp.zipbab.data.model.remote.report.ReportType
import com.bestapp.zipbab.data.model.remote.report.ReportUserRequest
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class ReportRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : ReportRemoteDataSource {
    override suspend fun reportUser(
        reporterId: String,
        reportedUserId: String
    ): Result<Boolean, NetworkError> {
        val request = ReportUserRequest(
            reporterId = reporterId,
            reportedUserId = reportedUserId,
        )
        return safeFirebaseCall {
            val existingReport = firestoreDB.getReportDB()
                .whereEqualTo("type", ReportType.USER.fieldName)
                .whereEqualTo("reporterId", reporterId)
                .whereEqualTo("reportedUserId", reportedUserId)
                .get()
                .await()

            // 중복 신고 비허용
            if (!existingReport.isEmpty) {
                return Result.Error(NetworkError.NOT_AVAILABLE) // ALREADY_REPORTED 보다는 포괄적인 것으로 설정함.
            }

            firestoreDB.getReportDB().add(request)
                .doneSuccessful()
        }
    }

    override suspend fun reportProfilePost(
        reporterId: String,
        postDocumentId: String
    ): Result<Boolean, NetworkError> {
        val request = ReportProfilePostRequest(
            reporterId = reporterId,
            postDocumentId = postDocumentId,
        )
        return safeFirebaseCall {
            val existingReport = firestoreDB.getReportDB()
                .whereEqualTo("type", ReportType.PROFILE_POST.fieldName)
                .whereEqualTo("reporterId", reporterId)
                .whereEqualTo("postDocumentId", postDocumentId)
                .get()
                .await()

            // 중복 신고 비허용
            if (!existingReport.isEmpty) {
                return Result.Error(NetworkError.NOT_AVAILABLE)
            }

            firestoreDB.getReportDB()
                .add(request)
                .doneSuccessful()
        }
    }
}
