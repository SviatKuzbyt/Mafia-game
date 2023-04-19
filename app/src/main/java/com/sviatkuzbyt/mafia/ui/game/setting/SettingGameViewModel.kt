package com.sviatkuzbyt.mafia.ui.game.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.elements.Roles
import com.sviatkuzbyt.mafia.data.game.repositories.SettingGameRepository
import com.sviatkuzbyt.mafia.data.settings.SettingStoreRepository
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent
import kotlinx.coroutines.launch

sealed class RecycleChangeMode{
    object LoadAll: RecycleChangeMode()
    object AddItem: RecycleChangeMode()
    object RemoveItem: RecycleChangeMode()
    object ChangeCount: RecycleChangeMode()
}

class SettingGameViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var repository: SettingGameRepository
    private var autoPlayers = false
    private lateinit var _rolesArray: Array<Roles>
    private lateinit var _playersList: MutableList<String>
    private val countLabel = application.getString(R.string.players_count)
    private val playerLabel = application.getString(R.string.player)
    var playersChangeMode: RecycleChangeMode = RecycleChangeMode.LoadAll
    val rolesArray = MutableLiveData<Array<Roles>>()
    val playersList = MutableLiveData<MutableList<String>>()
    val error = SingleLiveEvent<String>()
    private var roleCount = 0
    val playersCount = MutableLiveData<String>()
    var rolesChangeMode: RecycleChangeMode = RecycleChangeMode.LoadAll
    private var rolePosition = 0

    init {
        viewModelScope.launch{
            autoPlayers = SettingStoreRepository(application).getAutoPlayer()
            repository = SettingGameRepository(application, autoPlayers)
            _rolesArray = repository.getBasicRolesArray()
            _playersList = repository.getBasicPlayersList()
            rolesArray.postValue(_rolesArray)
            playersList.postValue(_playersList)
            roleCount = _playersList.size
            playersCount.postValue("$countLabel $roleCount")
            rolePosition = _playersList.size
        }
    }

    fun changeRole(position: Int, delta: Int){
        roleCount = _rolesArray[position].count + delta

        if (canAddMore(position)){
            playersChangeMode = if(delta > 0){
                _playersList.add(if(autoPlayers) "$playerLabel ${_playersList.size+1}" else "")
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

    override fun onCleared() {
        super.onCleared()
        repository.saveData(_rolesArray, _playersList)
    }
}