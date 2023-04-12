package com.sviatkuzbyt.mafia.ui.game.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.SettingGameRepository
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent

sealed class RecycleChangeMode{
    object LoadAll: RecycleChangeMode()
    object AddItem: RecycleChangeMode()
    object RemoveItem: RecycleChangeMode()
    object ChangeCount: RecycleChangeMode()
}

class SettingGameViewModel(application: Application): AndroidViewModel(application) {
    private val repository = SettingGameRepository(application)
    private val _rolesArray = repository.getBasicRolesArray()
    private val _playersList = repository.getBasicPlayersList()
    private val countLabel = application.getString(R.string.players_count)
    private val playerLabel = application.getString(R.string.player)
    var playersChangeMode: RecycleChangeMode = RecycleChangeMode.LoadAll
    val rolesArray = MutableLiveData(_rolesArray)
    val playersList = MutableLiveData(_playersList)
    val error = SingleLiveEvent<String>()
    val playersCount = MutableLiveData<String>()
    var rolesChangeMode: RecycleChangeMode = RecycleChangeMode.LoadAll
    private var roleCount = _playersList.size
    private var rolePosition = _playersList.size

    init {
        playersCount.value = "$countLabel $roleCount"
    }

    fun changeRole(position: Int, delta: Int){
        roleCount = _rolesArray[position].count + delta

        if (canAddMore(position)){
            playersChangeMode = if(delta > 0){
                _playersList.add("$playerLabel ${_playersList.size+1}")
                RecycleChangeMode.AddItem
            } else{
                _playersList.removeLast()
                RecycleChangeMode.RemoveItem
            }
            _rolesArray[position].count = roleCount
            rolePosition = position
            playersCount.value = "$countLabel ${_playersList.size}"

            rolesChangeMode = RecycleChangeMode.ChangeCount
            playersList.value = _playersList
            rolesArray.value = _rolesArray
        }
    }

    fun getCount() = roleCount

    private fun canAddMore(position: Int): Boolean{
        return roleCount <= _rolesArray[position].max && roleCount >= _rolesArray[position].min
    }

    fun changePlayerName(position: Int, name: String) {
        if (position in 0 until _playersList.size) {
            _playersList[position] = name
        } else {
            Log.e("changePlayerName","Error: Invalid position")
        }
    }

    fun createGameList() = repository.getGameList(_rolesArray, _playersList)

    fun getPosition() = rolePosition
}