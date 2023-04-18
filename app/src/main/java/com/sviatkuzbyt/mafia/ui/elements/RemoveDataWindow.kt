package com.sviatkuzbyt.mafia.ui.elements

import android.app.AlertDialog
import android.content.Context
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.settings.SettingsViewModel

class RemoveDataWindow(private val context: Context, private val viewModel: SettingsViewModel) {
    private lateinit var build: AlertDialog.Builder
    private lateinit var play: AlertDialog

    init {
        setWindow()
    }

    private fun setWindow(){
        build = AlertDialog.Builder(context, R.style.exit_game_window)
        build.setTitle(R.string.warning)
        build.setMessage(R.string.confirm_clear_data)

        build.setPositiveButton(R.string.yes){ _, _ ->
            viewModel.clearData()
        }

        build.setNegativeButton(R.string.no){ _, _ ->
            play.cancel()
        }

        play = build.create()
        play.setCancelable(true)
    }

    fun showWindow(){
        play.show()
    }
}