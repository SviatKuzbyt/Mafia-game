package com.sviatkuzbyt.mafia.ui.game.activity

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.PlayerData
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent
import com.sviatkuzbyt.mafia.ui.game.InformationFragment
import com.sviatkuzbyt.mafia.ui.game.playerpanel.PlayerPanelFragment
import com.sviatkuzbyt.mafia.ui.game.roles.RolesFragment
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameFragment

class GameViewModel(private val application: Application): AndroidViewModel(application) {
    val toolBarLabel = MutableLiveData<String>()
    val idForHelpButton = MutableLiveData<Int>()
    val isVisibleBackButton = MutableLiveData<Boolean>()
    val textOnNextButton = MutableLiveData<String>()
    val currentFragment = MutableLiveData<Fragment>()
    var gameArray = arrayOf<PlayerData>()
    val closeActivity = SingleLiveEvent<Boolean>()


    init {
        setSettingGameStep()
    }

    fun setSettingGameStep(){
        toolBarLabel.value = application.getString(R.string.setting_game)
        isVisibleBackButton.value = false
        currentFragment.value = SettingGameFragment()
        textOnNextButton.value = application.getString(R.string.new_game)
    }

    fun setStartGameStep(){
        toolBarLabel.value = ""
        currentFragment.value = InformationFragment.newInstance(
            application.getString(R.string.start_game_label),
            application.getString(R.string.start_game_information),
            "🚗",
            false
        )
        textOnNextButton.value = application.getString(R.string.next)

    }

    fun setGetCardStep(){
        toolBarLabel.value = application.getString(R.string.roles)
        isVisibleBackButton.value = true
        currentFragment.value = RolesFragment()
        textOnNextButton.value = application.getString(R.string.next)
    }

    fun setPlayerPanelStep(){
        toolBarLabel.value = application.getString(R.string.player_panel)
        isVisibleBackButton.value = true
        currentFragment.value = PlayerPanelFragment()
        textOnNextButton.value = application.getString(R.string.remove)
    }
    fun finishGame(result: String){
        isVisibleBackButton.value = false
        toolBarLabel.value = ""
        textOnNextButton.value = application.getString(R.string.close)
        currentFragment.value = InformationFragment.newInstance(
            application.getString(R.string.end_game),
            result,
            "🎉",
            true
        )
    }

    fun closeActivity(){
        closeActivity.value = true
    }
}