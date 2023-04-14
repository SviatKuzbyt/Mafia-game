package com.sviatkuzbyt.mafia.ui.game.playerpanel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.data.game.PlayerData
import com.sviatkuzbyt.mafia.ui.game.activity.GameViewModel

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