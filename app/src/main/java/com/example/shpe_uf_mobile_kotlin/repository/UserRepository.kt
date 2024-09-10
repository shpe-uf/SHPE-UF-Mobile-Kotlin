package com.example.shpe_uf_mobile_kotlin.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val LOGGED_IN = booleanPreferencesKey("logged_in")
        val LOGGED_OUT = booleanPreferencesKey("logged_out")
        val DARK_MODE = booleanPreferencesKey("dark_mode") // true = dark mode, false = light mode
    }

    val currentUserId: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    suspend fun saveUserId(id: String){
        dataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }

    val currentLoggedIn: Flow<Boolean> = dataStore.data.map{ preferences ->
        preferences[LOGGED_IN] ?: false
    }

    suspend fun saveLoggedIn(isLoggedIn: Boolean){
        dataStore.edit{ preferences ->
            preferences[LOGGED_IN] = isLoggedIn
        }
    }

    val currentDarkMode: Flow<Boolean> = dataStore.data.map{ preferences ->
        preferences[DARK_MODE] ?: false
    }

    suspend fun saveDarkMode(darkMode: Boolean){
        dataStore.edit{ preferences ->
            preferences[DARK_MODE] = darkMode
        }
    }

    val currentLoggedOut: Flow<Boolean> = dataStore.data.map{ preferences ->
        preferences[LOGGED_OUT] ?: false
    }

    suspend fun saveLoggedOut(isLoggedOut: Boolean){
        dataStore.edit{ preferences ->
            preferences[LOGGED_OUT] = isLoggedOut
        }
    }


}