package com.example.shpe_uf_mobile_kotlin.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import android.util.Log
import kotlinx.coroutines.flow.map

const val PREFERENCE_NAME = "User_Preference"

class SHPERepository (private val context: Context) {

    // Ensures there is only one instance of the DataStore.
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("User_Info")
    }

    // Create a string preference
    private object PreferenceKeys{
        val USER_KEY = stringPreferencesKey("user_id")

    }

    // Save to DataStore
    suspend fun saveToDataStore(name: String){
        context.dataStore.edit {preferences ->
            preferences[PreferenceKeys.USER_KEY] = name
        }
    }

    // Read from DataStore
    val readFromDataStore: Flow<String> = context.dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val name = preferences[PreferenceKeys.USER_KEY] ?: "none"
            name
        }


}