package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.category.CategoryResponse
import com.bestapp.zipbab.data.model.remote.category.CategoryState
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
    override suspend fun getCategory(name: String): Result<CategoryResponse, NetworkError> {
        return safeFirebaseCall<CategoryResponse> {
            val documentSnapshot = firestoreDB.getCategoryDB().document(name)
                .get()
                .await()

            val items = documentSnapshot.data?.get("items") as? List<Map<String, Any>> ?: emptyList()
            val state = documentSnapshot.data?.get("state") as? String ?: CategoryState.READY.name
            val iconResponses = items.map { item ->
                val imageUrl = (item["imageUrl"] as? String)?.let {
                    firestoreDB.getImageUrl(it)
                } ?: ""
                IconResponse(
                    imageUrl = imageUrl,
                    name = item["name"] as? String ?: "",
                )
            }

            CategoryResponse(
                icons = iconResponses,
                state = CategoryState.entries.firstOrNull { it.name == state } ?: CategoryState.READY,
            )
        }
    }
}
