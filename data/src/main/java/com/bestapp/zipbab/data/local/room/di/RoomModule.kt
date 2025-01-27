package com.bestapp.zipbab.data.local.room.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bestapp.zipbab.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private val migrationFrom1To2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {

            // 1. CategoryEntity 생성
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS CategoryEntity (
                    name TEXT NOT NULL PRIMARY KEY,
                    imageUrl TEXT NOT NULL
                )
            """
            )

            // 2. NotificationEntity column 수정

            // SQlite는 ALERT TABLE을 이용해 foreign key를 추가할 수 없다. 따라서 아래 순서대로 migration 진행
            // 새로운 table을 생성 -> 기존 table 데이터 새로운 table로 옮기기 -> 기존 table 삭제 -> 새로운 table 이름 변경
            // 참고 자료 : https://stackoverflow.com/a/57797179/11722881
            val notificationTableExists =
                db.query("SELECT name FROM sqlite_master WHERE type='table' AND name='NotificationEntity'")
                    .use {
                        it.moveToFirst()
                    }
            if (notificationTableExists) {
                db.execSQL(
                    """
                        CREATE TABLE NotificationEntity_new (
                            documentId TEXT NOT NULL PRIMARY KEY,
                            userId TEXT NOT NULL,
                            type TEXT NOT NULL,
                            createdAt TEXT NOT NULL,
                            isRead INTEGER NOT NULL,
                            FOREIGN KEY(userId) REFERENCES UserPrivateEntity(id) ON DELETE CASCADE
                        )
                    """
                )
                db.execSQL("CREATE INDEX index_NotificationEntity_userId ON NotificationEntity_new(userId)")

                db.execSQL(
                    """
                        INSERT INTO NotificationEntity_new (documentId, type, createdAt, isRead)
                        SELECT documentId, type, createdAt, isRead FROM NotificationEntity
                    """
                )
                db.execSQL("DROP TABLE NotificationEntity")
                db.execSQL("ALTER TABLE NotificationEntity_new RENAME TO NotificationEntity")
            }

            val flashMeetingTableExists = db.query("SELECT name FROM sqlite_master WHERE type='table' AND name='JoinedFlashMeetingEntity'").use {
                it.moveToFirst()
            }
            if (flashMeetingTableExists) {
                // 3. JoinedFlashMeetingEntity column 수정
                db.execSQL(
                    """
                    CREATE TABLE JoinedFlashMeetingEntity_new (
                        meetId TEXT NOT NULL PRIMARY KEY,
                        userId TEXT NOT NULL,
                        joinedAt TEXT NOT NULL,
                        type TEXT NOT NULL,
                        hasReviewed INTEGER NOT NULL,
                        reviewId TEXT NOT NULL,
                        FOREIGN KEY(userId) REFERENCES UserPrivateEntity(id) ON DELETE CASCADE
                    )
                """
                )
                db.execSQL("CREATE INDEX index_JoinedFlashMeetingEntity_userId ON JoinedFlashMeetingEntity_new(userId)")

                db.execSQL(
                    """
                    INSERT INTO JoinedFlashMeetingEntity_new (meetId, joinedAt, type, hasReviewed, reviewId)
                    SELECT meetId, joinedAt, type, hasReviewed, reviewId FROM JoinedFlashMeetingEntity
                """
                )
                db.execSQL("DROP TABLE JoinedFlashMeetingEntity")
                db.execSQL("ALTER TABLE JoinedFlashMeetingEntity_new RENAME TO JoinedFlashMeetingEntity")
            }
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "zipbab-database"
        ).addMigrations(migrationFrom1To2)
            .build()
    }
}
