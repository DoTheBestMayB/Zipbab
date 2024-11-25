package com.bestapp.zipbab.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bestapp.zipbab.data.local.room.entity.UserPrivateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPrivateDao {

    @Query("SELECT * FROM UserPrivateEntity LIMIT 1")
    fun getUserPrivate(): Flow<UserPrivateEntity?>

    @Update
    suspend fun updateUserPrivate(userPrivateEntity: UserPrivateEntity)

    @Query("DELETE FROM UserPrivateEntity")
    suspend fun deleteUserPrivate()
}
