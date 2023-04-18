package com.sviatkuzbyt.mafia.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sviatkuzbyt.mafia.data.settings.SettingsRepository
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {
    val autoPlayer = MutableLiveData<Boolean>()
    val autoWin = MutableLiveData<Boolean>()
    val error = SingleLiveEvent<String>()
    private val repository = SettingsRepository(application)

    init {
        viewModelScope.launch(Dispatchers.IO){
            autoPlayer.postValue(repository.getAutoPlayer())
            autoWin.postValue(repository.getAutoWin())
        }
    }

    fun setAutoPlayer(param: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            repository.setAutoPlayer(param)
        }
    }

    fun setAutoWin(param: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            repository.setAutoWin(param)
        }
    }

    fun clearData(){
        try {
            repository.clearData()
        } catch (e: Exception){
            error.postValue(e.message.toString())
        }
    }
}
