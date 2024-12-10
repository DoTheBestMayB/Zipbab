package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.category.CategoryResponse
import com.bestapp.zipbab.data.model.remote.category.IconResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class CategoryRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : CategoryRemoteDataSource {

    @Suppress("UNCHECKED_CAST")
    override suspend fun getFlashMeetCategory(): Result<CategoryResponse, NetworkError> {
        return safeFirebaseCall<CategoryResponse> {
            val documentSnapshot = firestoreDB.getCategoryDB().document("food")
                .get()
                .await()

            val iconResponses = documentSnapshot.data?.map { (_, value) ->
                val categoryData = value as? Map<String, String> ?: emptyMap()
                val imageUrl = categoryData["imageUrl"]?.let {
                    firestoreDB.getImageUrl(it)
                } ?: ""
                IconResponse(
                    imageUrl = imageUrl,
                    name = categoryData["name"] ?: "",
                )
            } ?: emptyList()

            CategoryResponse(iconResponses)
        }
    }
}
