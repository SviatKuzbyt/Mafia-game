package com.sviatkuzbyt.mafia.ui.game.roles

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.data.game.PlayerData

@Suppress("UNCHECKED_CAST")
class RolesViewModelFactory(private val application: Application, private val gameArray: Array<PlayerData>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RolesViewModel::class.java)) {
            return RolesViewModel(application, gameArray) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}