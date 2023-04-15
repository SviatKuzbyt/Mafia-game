package com.sviatkuzbyt.mafia.ui.game.activity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(private val application: Application,
                           private val isLoadSaveGame: Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, isLoadSaveGame) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}