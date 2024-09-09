package com.example.shpe_uf_mobile_kotlin.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val USER_DATASTORE = "user_id"

val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class SHPE_DataStore(val context: Context) {

    companion object {
        val ID = stringPreferencesKey("id")
    }

    suspend fun saveToDataStore(id: String){
        context.preferenceDataStore.edit {
            it[ID] = id
        }
    }

    fun getFromDataStore() = context.preferenceDataStore.data.map {
        Shpeito(
            id = it[ID]?:""
        )
    }

    suspend fun clearDataStore() = context.preferenceDataStore.edit{
        it.clear()
    }
}