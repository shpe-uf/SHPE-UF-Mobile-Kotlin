package com.example.shpe_uf_mobile_kotlin.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Session @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object{
        const val DATA = "Data"
        private const val IsLogin = "IsLogin"
        private const val ID = "ID"

        val isLogin = booleanPreferencesKey(IsLogin)
        val id = stringPreferencesKey(ID)
    }

    fun isShpeitoLoggedIn(): Flow<Boolean> {
        return dataStore.data.catch{
            emit(emptyPreferences())
        }.map { preference ->
            preference[isLogin] ?: false

        }
    }

    suspend fun setShpeitoLoggedIn(isLoggedIn: Boolean){
        dataStore.edit { preference ->
            preference[isLogin] = isLoggedIn
        }
    }

    fun getID(): Flow<String>{
        return dataStore.data.catch{
            emit(emptyPreferences())
        }.map { preference ->
            preference[id] ?: ""
        }
    }

    suspend fun setID(newID: String){
        dataStore.edit { preference ->
            preference[id] = newID
        }
    }
}