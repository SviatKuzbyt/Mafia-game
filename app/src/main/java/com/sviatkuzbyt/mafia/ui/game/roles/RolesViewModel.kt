package com.sviatkuzbyt.mafia.ui.game.roles

import android.app.Application
import androidx.lifecycle.*
import com.sviatkuzbyt.mafia.data.game.elements.CardRole
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.data.game.repositories.generateCardRolesArray

import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel

class RolesViewModel(
    application: Application,
    gameArray: Array<PlayerData>,
    private val activityViewModel: GameViewModel): AndroidViewModel(application) {

    private val rolesCardArray = generateCardRolesArray(gameArray, application)
    private var arrayIndex = 0
    val player = MutableLiveData<CardRole>()
    var isNextAnimation = true

    init {
        setData()
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

@Suppress("UNCHECKED_CAST")
class RolesViewModelFactory(private val application: Application,
                            private val gameArray: Array<PlayerData>,
                            private val activityViewModel: GameViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RolesViewModel::class.java)) {
            return RolesViewModel(application, gameArray, activityViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}