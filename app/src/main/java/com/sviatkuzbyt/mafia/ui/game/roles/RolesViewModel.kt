package com.sviatkuzbyt.mafia.ui.game.roles

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sviatkuzbyt.mafia.data.game.CardRole
import com.sviatkuzbyt.mafia.data.game.PlayerData
import com.sviatkuzbyt.mafia.data.game.RolesRepository
import kotlinx.coroutines.launch

class RolesViewModel(application: Application, gameArray: Array<PlayerData>): AndroidViewModel(application) {
    private lateinit var rolesCardArray: Array<CardRole>
    private var arrayIndex = 0
    val player = MutableLiveData<String>()
    val role = MutableLiveData<String>()
    val image = MutableLiveData<Drawable?>()

    init {
        viewModelScope.launch{
            rolesCardArray = RolesRepository(application).generateCardRolesArray(gameArray)
            setData()
        }
    }

    private fun setData(){
        player.postValue(rolesCardArray[arrayIndex].player)
        role.postValue(rolesCardArray[arrayIndex].role)
        image.postValue(rolesCardArray[arrayIndex].image)
    }

    fun nextPlayer(){
        if(arrayIndex + 1 < rolesCardArray.size){
            arrayIndex ++
            setData()
        }
    }

    fun previousPlayer(){
        if(arrayIndex - 1 >= 0){
            arrayIndex --
            setData()
        }
    }
}