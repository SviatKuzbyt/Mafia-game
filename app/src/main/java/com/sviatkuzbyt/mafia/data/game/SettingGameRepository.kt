package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import com.sviatkuzbyt.mafia.R

class SettingGameRepository(private val context: Context) {

    val rolesArray = arrayOf(
        Roles(context.getString(R.string.mafia), R.drawable.gun_ic, 1, 5),
        Roles(context.getString(R.string.commissioner), R.drawable.hat_ic, 1, 1),
        Roles(context.getString(R.string.doctor), R.drawable.plus_ic, 0, 1),
        Roles(context.getString(R.string.peaceful), R.drawable.people_icon, 3, 10)
    )

    var playersCount = rolesArray.sumOf { it.count }
    val playersList = MutableList(playersCount) { "${context.getString(R.string.player)} ${it + 1}" }

    fun changeRoleCount(position: Int, delta: Int) {
        rolesArray[position].count += delta
        playersCount += delta
        if (delta > 0) {
            playersList += "${context.getString(R.string.player)} $playersCount"
        } else {
            playersList.removeLast()
        }
    }
}

data class Roles(
    val name: String,
    val icon: Int,
    val min: Int,
    val max: Int,
    var count: Int = min
)