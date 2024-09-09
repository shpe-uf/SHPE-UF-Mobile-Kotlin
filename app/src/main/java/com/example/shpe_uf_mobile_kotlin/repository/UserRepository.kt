package com.example.shpe_uf_mobile_kotlin.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val USER_ID = stringPreferencesKey("user_id")
    }

    val currentUserId: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    suspend fun saveUserId(id: String){
        dataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }
}