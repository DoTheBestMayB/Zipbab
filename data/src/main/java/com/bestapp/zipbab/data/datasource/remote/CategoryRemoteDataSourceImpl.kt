package com.bestapp.zipbab.data.datasource.remote

import com.bestapp.zipbab.data.FirestoreDB.FirestoreDB
import com.bestapp.zipbab.data.model.remote.FilterResponse
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : CategoryRemoteDataSource {
    override suspend fun getFoodCategory(): FilterResponse.FoodCategory {
        val documentSnapshot = firestoreDB.getCategoryDB().document("food")
            .get()
            .await()

        return documentSnapshot.toObject<FilterResponse.FoodCategory>() ?: FilterResponse.FoodCategory()
    }

    override suspend fun getCostCategory(): FilterResponse.CostCategory {
        val documentSnapshot = firestoreDB.getCategoryDB().document("cost")
            .get()
            .await()

        return documentSnapshot.toObject<FilterResponse.CostCategory>() ?: FilterResponse.CostCategory()
    }
}