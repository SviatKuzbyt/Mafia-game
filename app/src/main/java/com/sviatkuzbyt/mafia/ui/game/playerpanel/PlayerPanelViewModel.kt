package com.sviatkuzbyt.mafia.ui.game.playerpanel

import android.app.Application
import androidx.lifecycle.*
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.data.game.elements.PlayerPanelData
import com.sviatkuzbyt.mafia.data.game.repositories.PlayerPanelRepository
import com.sviatkuzbyt.mafia.data.settings.SettingStoreRepository
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import kotlinx.coroutines.launch

class PlayerPanelViewModel(application: Application, gameArray: Array<PlayerData>, private val activityViewModel: GameViewModel): AndroidViewModel(application) {
    private val repository = PlayerPanelRepository(gameArray, application)
    private val _playerList = repository.getPlayerPanelList()
    private val removedPlayers = mutableListOf<PlayerPanelData>()
    val playerList = MutableLiveData(_playerList)
    val removedItems = mutableListOf<Int>()
    var mode = 0
    var autoWin = true

    init {
        viewModelScope.launch {
            autoWin = SettingStoreRepository(application).getAutoWin()
        }
    }

    fun updateSelectElement(position: Int, isSelect: Boolean){
        _playerList[position].isSelected = isSelect
    }

    fun removeItems(){
        removedItems.clear()
        mode = 1
        fillRemovedItemsList()
        removePlayers()

        if (autoWin)
            repository.matchResult()?.let { activityViewModel.finishGame(it) }

        playerList.value = _playerList
    }

    private fun fillRemovedItemsList(){
        var removedElements = 0
        for(i in 0 until _playerList.size){
            if(_playerList[i].isSelected){
                removedItems.add(i-removedElements)
                removedElements++
            }
        }
    }

    private fun removePlayers(){
        removedItems.forEach {
            removedPlayers.add(_playerList[it])
            repository.editPlayer(_playerList[it])
            _playerList.removeAt(it)
        }
    }

    fun returnRemovedPlayer(){
        if(removedPlayers.isNotEmpty()){
            mode = 2
            val player = removedPlayers.last().apply { isSelected = false }
            removedPlayers.removeLast()

            _playerList.add(player)
            repository.editPlayer(_playerList.last())
            playerList.value = _playerList
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PlayerPanelViewModelFactory(
    private val application: Application,
    private val gameArray: Array<PlayerData>,
    private val activityViewModel: GameViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerPanelViewModel::class.java)) {
            return PlayerPanelViewModel(application, gameArray, activityViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}