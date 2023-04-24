package com.sviatkuzbyt.mafia.ui.main
import android.content.Intent
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.activity.GameActivity
import com.sviatkuzbyt.mafia.ui.help.list.HelpListActivity
import com.sviatkuzbyt.mafia.ui.settings.SettingsActivity

fun getMenuArray(activity: MainActivity) = arrayOf(
        MainMenuData(R.drawable.continue_ic,
            R.string.continue_game,
            Intent(activity, GameActivity::class.java).apply { putExtra("loadSaveGame", true)}
        ),
        MainMenuData(R.drawable.rules_ic, R.string.help, Intent(activity, HelpListActivity::class.java)),
        MainMenuData(R.drawable.settings_ic, R.string.settings, Intent(activity, SettingsActivity::class.java))
    )

data class MainMenuData(
    val icon: Int,
    val name: Int,
    val intent: Intent?
)
