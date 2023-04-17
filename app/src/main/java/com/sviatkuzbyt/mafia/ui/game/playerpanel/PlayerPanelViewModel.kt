package com.sviatkuzbyt.mafia.ui.game.playerpanel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.data.game.elements.PlayerPanelData
import com.sviatkuzbyt.mafia.data.game.repositories.PlayerPanelRepository
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel

class PlayerPanelViewModel(application: Application, gameArray: Array<PlayerData>, private val activityViewModel: GameViewModel): AndroidViewModel(application) {
        private val repository = PlayerPanelRepository(gameArray, application)
        private val _playerList = repository.getPlayerPanelList()
        private val removedPlayers = mutableListOf<PlayerPanelData>()
        val playerList = MutableLiveData(_playerList)
        val removedItems = mutableListOf<Int>()
        var mode = 0

        fun updateSelectElement(position: Int, isSelect: Boolean){
            _playerList[position].isSelected = isSelect
        }

        fun removeItems(){
            removedItems.clear()
            mode = 1
            var temp = 0
            for(i in 0 until _playerList.size){
                if(_playerList[i].isSelected){
                    removedItems.add(i-temp)
                    temp++
                }
            }

            removedItems.forEach {
                removedPlayers.add(_playerList[it])
                repository.editPlayer(_playerList[it])
                _playerList.removeAt(it)
            }
            val result = repository.matchResult()
            if(result != null) activityViewModel.finishGame(result)
            playerList.value = _playerList
        }

        fun getRemovedPlayer(){
            if(removedPlayers.isNotEmpty()){
                val player = removedPlayers.last()
                player.isSelected = false
                removedPlayers.removeLast()
                _playerList.add(player)
                repository.editPlayer(_playerList.last())
                mode = 2
                playerList.value = _playerList
            }
        }
    }