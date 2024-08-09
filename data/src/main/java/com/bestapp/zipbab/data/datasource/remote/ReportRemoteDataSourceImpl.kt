package com.bestapp.zipbab.data.datasource.remote

import com.bestapp.zipbab.data.FirestoreDB.FirestoreDB
import com.bestapp.zipbab.data.doneSuccessful
import com.bestapp.zipbab.data.model.remote.ReportForm

class ReportRemoteDataSourceImpl(
    private val firestoreDB: FirestoreDB,
) : ReportRemoteDataSource {
    override suspend fun reportUser(userDocumentID: String): Boolean {
        return firestoreDB.getReportDB()
            .add(ReportForm(userDocumentID = userDocumentID))
            .doneSuccessful()
    }

    override suspend fun reportPost(userDocumentID: String, postDocumentID: String): Boolean {
        return firestoreDB.getReportDB()
            .add(ReportForm(userDocumentID = userDocumentID, postDocumentID = postDocumentID))
            .doneSuccessful()
    }
}