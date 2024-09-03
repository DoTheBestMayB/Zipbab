package com.bestapp.zipbab.data.remote.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private object PreferencesKeys {
    val USER_DOCUMENT_ID = stringPreferencesKey("user_document_id")
    val USER_ID = stringPreferencesKey("user_id")
}

class UserLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserLocalDataSource {

    override val userDocumentID: Flow<String>
        get() = dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.USER_DOCUMENT_ID] ?: ""
        }

    override suspend fun updateUserDocumentId(userDocumentID: String): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.USER_DOCUMENT_ID] = userDocumentID
            }
            true
        } catch (_: IOException) {
            false
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun removeUserDocumentId(): Boolean {
        return try {
            dataStore.edit {
                it.remove(PreferencesKeys.USER_DOCUMENT_ID)
            }
            true
        } catch (_: IOException) {
            false
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun getRememberId(): String {
        return try {
            dataStore.data.catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.first()[PreferencesKeys.USER_ID] ?: ""
        } catch (e: NoSuchElementException) {
            ""
        }
    }

    override suspend fun updateRememberId(id: String): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.USER_ID] = id
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
                it.remove(PreferencesKeys.USER_ID)
            }
            true
        } catch (_: IOException) {
            false
        } catch (_: Exception) {
            false
        }
    }
}