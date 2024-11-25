package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.category.FoodCategoryResponse
import com.bestapp.zipbab.data.model.remote.category.FoodIconResponse
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
    override suspend fun getFoodCategory(): Result<FoodCategoryResponse, NetworkError> {
        return safeFirebaseCall<FoodCategoryResponse> {
            val documentSnapshot = firestoreDB.getCategoryDB().document("food")
                .get()
                .await()

            val iconResponses = documentSnapshot.data?.map { (_, value) ->
                val categoryData = value as? Map<String, String> ?: emptyMap()
                val imageUrl = categoryData["imageUrl"]?.let {
                    firestoreDB.getImageUrl(it)
                } ?: ""
                FoodIconResponse(
                    imageUrl = imageUrl,
                    name = categoryData["name"] ?: "",
                )
            } ?: emptyList()

            FoodCategoryResponse(iconResponses)
        }
    }
}
