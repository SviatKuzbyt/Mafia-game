package com.sviatkuzbyt.mafia.ui.game.activity

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.elements.FileManager
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.ui.elements.SingleLiveEvent
import com.sviatkuzbyt.mafia.ui.game.InformationFragment

class GameViewModel(private val application: Application, isLoadSaveGame: Boolean): AndroidViewModel(application) {

    val toolBarLabel = MutableLiveData<String>()
    val isVisibleBackButton = MutableLiveData<Boolean>()
    val textOnNextButton = MutableLiveData<String>()
    val currentFragment = MutableLiveData<Fragment>()
    val closeActivity = SingleLiveEvent<Boolean>()
    val error = SingleLiveEvent<String>()

    var exitWindowText: Int? = null
    var idForHelpButton: Int? = null
    var gameArray = arrayOf<PlayerData>()

    private var isPlayerPanelStep = false
    private var fileManager = FileManager(application)
    private var stepArray = getGameStepsArray(application)

    init {
        if(isLoadSaveGame) loadGameArray()
        else setSettingGameStep()
    }

    private fun loadGameArray(){
        val array = fileManager.readGameArray()
        if(array != null){
            gameArray = array
            setPlayerPanelStep()
        }
        else
            error.value = application.getString(R.string.no_save)
    }

    private fun setSettingGameStep(){ setStep(stepArray[0]) }
    fun setStartGameStep(){ setStep(stepArray[1]) }
    fun setGetCardStep(){ setStep(stepArray[2]) }

    fun setPlayerPanelStep(){
        setStep(stepArray[3])
        isPlayerPanelStep = true
    }

    fun finishGame(result: String){
        stepArray[4].fragment = InformationFragment.newInstance(
            application.getString(R.string.end_game), result, "🎉", true)
        setStep(stepArray[4])
        isPlayerPanelStep = false
    }

    private fun setStep(gameStep: GameStep){
        textOnNextButton.value = application.getString(gameStep.nextButton)
        isVisibleBackButton.value = gameStep.visibleBack
        currentFragment.value = gameStep.fragment

        toolBarLabel.value = if (gameStep.label == null) ""
            else application.getString(gameStep.label)

        exitWindowText = gameStep.exitText
        idForHelpButton = gameStep.help
    }

    fun closeActivity(){
        closeActivity.value = true
    }

    override fun onCleared() {
        super.onCleared()
        if(isPlayerPanelStep) fileManager.writeFile(gameArray, "arrayGame")
    }
}

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