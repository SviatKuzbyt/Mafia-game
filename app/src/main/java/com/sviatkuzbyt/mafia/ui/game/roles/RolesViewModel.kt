package com.sviatkuzbyt.mafia.ui.game.roles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sviatkuzbyt.mafia.data.game.elements.CardRole
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.data.game.repositories.RolesRepository
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel
import kotlinx.coroutines.launch

class RolesViewModel(
    application: Application,
    gameArray: Array<PlayerData>,
    private val activityViewModel: GameViewModel): AndroidViewModel(application) {

    private lateinit var rolesCardArray: Array<CardRole>
    private var arrayIndex = 0
    val player = MutableLiveData<CardRole>()
    var isNextAnimation = true

    init {
        viewModelScope.launch{
            rolesCardArray = RolesRepository(application).generateCardRolesArray(gameArray)
            setData()
        }
    }

    private fun setData(){
        player.postValue(CardRole(
            rolesCardArray[arrayIndex].player,
            rolesCardArray[arrayIndex].role,
            rolesCardArray[arrayIndex].image
        ))
    }

    fun nextPlayer(){
        if(arrayIndex + 1 < rolesCardArray.size){
            if (!isNextAnimation) isNextAnimation = true
            arrayIndex ++
            setData()
        }
        else
            activityViewModel.setPlayerPanelStep()
    }

    fun previousPlayer(){
        if(arrayIndex - 1 >= 0){
            if (isNextAnimation) isNextAnimation = false
            arrayIndex --
            setData()
        }
    }
}