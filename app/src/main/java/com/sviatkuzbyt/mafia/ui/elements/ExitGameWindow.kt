package com.sviatkuzbyt.mafia.ui.elements

import android.app.AlertDialog
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.activity.GameActivity


class ExitGameWindow(private val activity: GameActivity) {
    lateinit var build: AlertDialog.Builder
    lateinit var play: AlertDialog
    var targetMessage = 0

    init {
        setWindowBuild()
    }

    private fun setWindowBuild(){
        build = AlertDialog.Builder(activity, R.style.exit_game_window)
        build.setTitle(R.string.exit_game_label)

        build.setPositiveButton(R.string.yes){_, _ ->
            activity.finish()
        }

        build.setNegativeButton(R.string.no){_, _ ->
            play.cancel()
        }
    }

    fun showWindow(message: Int){
        if(message != targetMessage) setWindowPlay(message)
        play.show()
    }

    private fun setWindowPlay(message: Int){
        build.setMessage(message)
        play = build.create()
        play.setCancelable(true)
    }
}