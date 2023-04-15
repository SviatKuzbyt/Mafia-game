package com.sviatkuzbyt.mafia.ui.game.activity

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.GameRepository
import com.sviatkuzbyt.mafia.data.game.PlayerData
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent
import com.sviatkuzbyt.mafia.ui.game.InformationFragment
import com.sviatkuzbyt.mafia.ui.game.playerpanel.PlayerPanelFragment
import com.sviatkuzbyt.mafia.ui.game.roles.RolesFragment
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameFragment

class GameViewModel(private val application: Application, isLoadSaveGame: Boolean): AndroidViewModel(application) {
    val toolBarLabel = MutableLiveData<String>()
    val idForHelpButton = MutableLiveData<Int>()
    val isVisibleBackButton = MutableLiveData<Boolean>()
    val textOnNextButton = MutableLiveData<String>()
    val currentFragment = MutableLiveData<Fragment>()
    var gameArray = arrayOf<PlayerData>()
    val closeActivity = SingleLiveEvent<Boolean>()
    private var isPlayerPanelStep = false
    private var fileManager = GameRepository(application)
    val error = SingleLiveEvent<String>()


    init {
        if(isLoadSaveGame) loadGameArray()
        else setSettingGameStep()
        Log.v("app test", isLoadSaveGame.toString())
    }

    private fun setSettingGameStep(){
        setStep(R.string.new_game, false, R.string.setting_game, SettingGameFragment())
    }

    fun setStartGameStep(){
        setStep(R.string.next, false, null, InformationFragment.newInstance(
            application.getString(R.string.start_game_label),
            application.getString(R.string.start_game_information),
            "🚗",
            false
        ))
    }

    fun setGetCardStep(){
        setStep(R.string.next, true, R.string.roles, RolesFragment())
    }

    fun setPlayerPanelStep(){
        setStep(R.string.remove, true, R.string.player_panel, PlayerPanelFragment())
        isPlayerPanelStep = true
    }
    fun finishGame(result: String){
        setStep(R.string.close, false, null, InformationFragment.newInstance(
            application.getString(R.string.end_game),
            result,
            "🎉",
            true
        ))
        isPlayerPanelStep = false
    }

    private fun setStep(
        nextButton: Int,
        visibleBack: Boolean,
        label: Int?,
        fragment: Fragment
    ){
        textOnNextButton.value = application.getString(nextButton)
        isVisibleBackButton.value = visibleBack
        currentFragment.value = fragment
        toolBarLabel.value =
            if (label == null) ""
            else application.getString(label)
    }

    fun closeActivity(){
        closeActivity.value = true
    }

    override fun onCleared() {
        super.onCleared()
        if(isPlayerPanelStep) fileManager.writeArray(gameArray)
    }

    private fun loadGameArray(){
        val _gameArray = fileManager.readArray()
        if(_gameArray != null){
            gameArray = _gameArray
            setPlayerPanelStep()
        }
        else
            error.value = application.getString(R.string.no_save)
    }
}