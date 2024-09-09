package com.example.shpe_uf_mobile_kotlin.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shpe_uf_mobile_kotlin.data.SHPERepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SHPEUFViewModel(application: Application): AndroidViewModel(application) {

    private val respository = SHPERepository(application)

    val readFromDataStore = respository.readFromDataStore.asLiveData()

    fun saveToDataStore(id: String) = viewModelScope.launch(Dispatchers.IO){
        respository.saveToDataStore(id)
    }

}

