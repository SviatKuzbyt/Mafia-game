package com.sviatkuzbyt.mafia.ui.game.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sviatkuzbyt.mafia.data.game.SettingGameRepository
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent

sealed class PlayersListSettingChange{
    object LoadAll: PlayersListSettingChange()
    object AddItem: PlayersListSettingChange()
    object RemoveItem: PlayersListSettingChange()
}

class SettingGameViewModel(application: Application): AndroidViewModel(application) {
    private val repository = SettingGameRepository(application)
    var playersListSettingChange: PlayersListSettingChange = PlayersListSettingChange.LoadAll
    val rolesArray = MutableLiveData(repository.rolesArray)
    val playersList = MutableLiveData(repository.playersList)
    val error = SingleLiveEvent<String>()
    val playersCount = MutableLiveData<String>()

    init {
        playersCount.value = repository.getPlayersCount()
    }

    fun changeRole(position: Int, delta: Int){
        try {
            repository.changeRoleCount(position, delta)
            playersListSettingChange = if(delta > 0)
                PlayersListSettingChange.AddItem
            else
                PlayersListSettingChange.RemoveItem
            playersList.value = repository.playersList
            playersCount.value = repository.getPlayersCount()
        } catch (e: Exception){
            error.postValue("Error: ${e.message}")
        }
    }

    fun changePlayerName(position: Int,  name: String){
        try {
            repository.playersList[position] = name
        } catch (e: Exception){
            error.postValue("Error: ${e.message}")
        }
    }
}