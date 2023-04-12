package com.sviatkuzbyt.mafia.ui.game.activity

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.PlayerData
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameFragment

class GameViewModel(private val application: Application): AndroidViewModel(application) {
    val toolBarLabel = MutableLiveData<String>()
    val idForHelpButton = MutableLiveData<Int>()
    val isVisibleBackButton = MutableLiveData<Boolean>()
    val textOnNextButton = MutableLiveData<String>()
    val currentFragment = MutableLiveData<Fragment>()
    var gameList = arrayOf<PlayerData>()

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
        Log.v("app test", "all work good! Size of gameList: ${gameList.size}")
    }
}