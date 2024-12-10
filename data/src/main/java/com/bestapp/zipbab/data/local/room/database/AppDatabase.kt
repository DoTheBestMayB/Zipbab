package com.bestapp.zipbab.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bestapp.zipbab.data.local.room.converter.NotificationTypeConverter
import com.bestapp.zipbab.data.local.room.converter.ZonedDateTimeConverter
import com.bestapp.zipbab.data.local.room.dao.FlashMeetCategoryDao
import com.bestapp.zipbab.data.local.room.dao.NotificationDao
import com.bestapp.zipbab.data.local.room.dao.UserPrivateDao
import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import com.bestapp.zipbab.data.local.room.entity.JoinedFlashMeetingEntity
import com.bestapp.zipbab.data.local.room.entity.NotificationEntity
import com.bestapp.zipbab.data.local.room.entity.UserPrivateEntity

@Database(
    version = 2,
    entities = [UserPrivateEntity::class, CategoryEntity::class, NotificationEntity::class, JoinedFlashMeetingEntity::class],
)
@TypeConverters(NotificationTypeConverter::class, ZonedDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userPrivateDao(): UserPrivateDao

    abstract fun flashMeetCategoryDao(): FlashMeetCategoryDao

    abstract fun notificationDao(): NotificationDao
}
