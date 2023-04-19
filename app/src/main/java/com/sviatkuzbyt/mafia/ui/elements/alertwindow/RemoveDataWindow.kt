package com.sviatkuzbyt.mafia.ui.elements.alertwindow

import android.app.AlertDialog
import android.content.Context
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.settings.SettingsViewModel

class RemoveDataWindow(context: Context, viewModel: SettingsViewModel) {
    private lateinit var play: AlertDialog

    private val build = AlertDialog.Builder(context, R.style.exit_game_window).apply{
        setTitle(R.string.warning)
        setMessage(R.string.confirm_clear_data)

        setPositiveButton(R.string.yes){ _, _ ->
            viewModel.clearData()
        }

        setNegativeButton(R.string.no){ _, _ ->
            play.cancel()
        }

        play = create().apply { setCancelable(true) }
    }

    fun showWindow(){
        play.show()
    }
}