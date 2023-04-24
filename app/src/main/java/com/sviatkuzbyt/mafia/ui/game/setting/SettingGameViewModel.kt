package com.sviatkuzbyt.mafia.ui.game.setting

import android.app.Application
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
    var roleCount = 0
    var targetRolePositionChange = 0

    var playersChangeMode: RecycleChangeMode = RecycleChangeMode.LoadAll
    val rolesArray = MutableLiveData<Array<Roles>>()
    val playersList = MutableLiveData<MutableList<String>>()
    val error = SingleLiveEvent<String>()
    val playersCount = MutableLiveData<String>()
    var rolesChangeMode: RecycleChangeMode = RecycleChangeMode.LoadAll

    init {
        viewModelScope.launch{
            autoPlayers = SettingStoreRepository(application).getAutoPlayer()
            repository = SettingGameRepository(application, autoPlayers)
            setLists()

            roleCount = _playersList.size
            playersCount.postValue("$countLabel $roleCount")
            targetRolePositionChange = _playersList.size
        }
    }

    private fun setLists(){
        _rolesArray = repository.getBasicRolesArray()
        rolesArray.postValue(_rolesArray)

        _playersList = repository.getBasicPlayersList()
        playersList.postValue(_playersList)
    }

    fun changeRole(position: Int, delta: Int){
        if (canAddMore(position, delta)){
            roleCount += delta
            _rolesArray[position].count = roleCount
            targetRolePositionChange = position

            changePlayersCount(delta)
            publishData(delta)
        }
    }

    private fun canAddMore(position: Int, delta: Int): Boolean{
        val count = roleCount + delta
        return count <= _rolesArray[position].max && count >= _rolesArray[position].min
    }

    private fun changePlayersCount(delta: Int){
        if(delta > 0)
            _playersList.add(if(autoPlayers) "$playerLabel ${_playersList.size+1}" else "")
        else
            _playersList.removeLast()
    }

    private fun publishData(delta: Int){
        playersChangeMode = if(delta > 0) RecycleChangeMode.AddItem
                            else RecycleChangeMode.RemoveItem
        rolesChangeMode = RecycleChangeMode.ChangeCount

        playersCount.value = "$countLabel ${_playersList.size}"
        playersList.value = _playersList
        rolesArray.value = _rolesArray
    }

    fun changePlayerName(position: Int, name: String) {
        if (position in 0 until _playersList.size)
            _playersList[position] = name
    }

    fun createGameList() = repository.getGameList(_rolesArray, _playersList)

    override fun onCleared() {
        super.onCleared()
        repository.saveData(_rolesArray, _playersList)
    }
}