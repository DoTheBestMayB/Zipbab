package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.banner.BannerItemResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BannerRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
): BannerRemoteDataSource {

    override suspend fun getHomeBanner(): Result<List<BannerItemResponse>, NetworkError> {
        return safeFirebaseCall<List<BannerItemResponse>> {
            val documentSnapshot = firestoreDB.getBannerDB().whereEqualTo("type", "Home")
                .get()
                .await()

            documentSnapshot.documents.mapNotNull { document ->
                document.toObject<BannerItemResponse>()
            }.map {  response ->
                response.copy(
                    bannerUrl = firestoreDB.getImageUrl(response.bannerUrl),
                    contentUrl = if (response.contentUrl != null) {
                        firestoreDB.getImageUrl(response.contentUrl)
                    } else {
                        response.contentUrl
                    }
                )
            }
        }
    }
}
