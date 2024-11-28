package com.bestapp.zipbab.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bestapp.zipbab.data.local.room.dao.UserPrivateDao
import com.bestapp.zipbab.data.local.room.entity.UserPrivateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject


private object PreferencesKeys {
    val REMEMBER_ID = stringPreferencesKey("remember_id")
}

class UserPrivateLocalDataSourceImpl @Inject constructor(
    private val userPrivateDao: UserPrivateDao,
    private val dataStore: DataStore<Preferences>,
) : UserPrivateLocalDataSource {

    override val privateData: Flow<UserPrivateEntity?> = userPrivateDao.getUserPrivate()

    override suspend fun updatePrivateData(userPrivateEntity: UserPrivateEntity) {
        userPrivateDao.updateUserPrivate(userPrivateEntity)
    }

    override suspend fun removePrivateData() {
        userPrivateDao.deleteUserPrivate()
    }

    override suspend fun getRememberId(): String {
        return try {
            dataStore.data.catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.first()[PreferencesKeys.REMEMBER_ID] ?: ""
        } catch (e: NoSuchElementException) {
            ""
        }
    }

    override suspend fun updateRememberId(id: String): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.REMEMBER_ID] = id
            }
            true
        } catch (_: IOException) {
            false
        } catch (_: Exception) {
            false
        }

    }

    override suspend fun removeRememberId(): Boolean {
        return try {
            dataStore.edit {
                it.remove(PreferencesKeys.REMEMBER_ID)
            }
            true
        } catch (_: IOException) {
            false
        } catch (_: Exception) {
            false
        }
    }
}
