package com.secret.agentchat.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val SHARED_PREF_NAME = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREF_NAME)

class SharedPref(
    private val context: Context
) {

    suspend fun put(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    fun getToken(): Flow<String?> {
        val key = stringPreferencesKey(TOKEN_KEY)
        return context.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    fun getUserId(): Flow<String?> {
        val key = stringPreferencesKey(TOKEN_KEY)
        return context.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    companion object{
        private const val TOKEN_KEY = "token"
    }
}