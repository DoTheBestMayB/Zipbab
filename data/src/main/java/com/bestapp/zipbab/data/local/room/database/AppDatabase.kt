package com.bestapp.zipbab.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestapp.zipbab.data.local.room.dao.UserPrivateDao
import com.bestapp.zipbab.data.local.room.entity.UserPrivateEntity

@Database(entities = [UserPrivateEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userPrivateDao(): UserPrivateDao
}
