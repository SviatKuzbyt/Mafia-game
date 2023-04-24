package com.sviatkuzbyt.mafia.ui.game.activity

import android.content.Context
import androidx.fragment.app.Fragment
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.ui.game.InformationFragment
import com.sviatkuzbyt.mafia.ui.game.playerpanel.PlayerPanelFragment
import com.sviatkuzbyt.mafia.ui.game.roles.RolesFragment
import com.sviatkuzbyt.mafia.ui.game.setting.SettingGameFragment

fun getGameStepsArray(context: Context) = arrayOf(
    GameStep(
        R.string.new_game,
        false,
        R.string.setting_game,
        SettingGameFragment(),
        R.string.exit_game_description_settings,
        5),

    GameStep(R.string.next,
        false,
        null,
        InformationFragment.newInstance(
            context.getString(R.string.start_game_label),
            context.getString(R.string.start_game_information),
            "🚗", false),
        R.string.exit_game_description_roles,
        null),

    GameStep(R.string.next,
        true,
        R.string.roles,
        RolesFragment(),
        R.string.exit_game_description_roles,
        6),

    GameStep(R.string.remove,
        true,
        R.string.player_panel,
        PlayerPanelFragment(),
        R.string.exit_game_description_game,
        7),

    GameStep(R.string.close,
        false,
        null,
        InformationFragment.newInstance(context.getString(R.string.end_game), "", "🎉", true),
        null,
        null)
)

data class GameStep(
    val nextButton: Int,
    val visibleBack: Boolean,
    val label: Int?,
    var fragment: Fragment,
    val exitText: Int?,
    val help: Int?
)