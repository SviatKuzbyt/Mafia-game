package com.sviatkuzbyt.mafia.ui.elements.alertwindow

import android.app.AlertDialog
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.activity.GameActivity

class ExitGameWindow(activity: GameActivity) {
    private lateinit var play: AlertDialog
    private var targetMessage = 0

    private val build = AlertDialog.Builder(activity, R.style.exit_game_window).apply {
        setTitle(R.string.exit_game_label)

        setPositiveButton(R.string.yes) { _, _ ->
            activity.finish()
        }

        setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
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