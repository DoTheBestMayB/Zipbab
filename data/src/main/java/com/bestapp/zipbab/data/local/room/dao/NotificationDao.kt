package com.bestapp.zipbab.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.bestapp.zipbab.data.local.room.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationEntity")
    fun getNotification(): Flow<List<NotificationEntity>>
}
