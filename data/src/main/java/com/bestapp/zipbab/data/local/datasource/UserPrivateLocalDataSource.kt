package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.entity.UserPrivateEntity
import com.bestapp.zipbab.data.local.room.entity.UserPrivateRelations
import kotlinx.coroutines.flow.Flow

interface UserPrivateLocalDataSource {

    val privateRelations: Flow<UserPrivateRelations?>

    suspend fun updatePrivateData(userPrivateEntity: UserPrivateEntity)

    suspend fun removePrivateData()

    suspend fun getRememberId(): String

    suspend fun updateRememberId(id: String): Boolean

    suspend fun removeRememberId(): Boolean
}
