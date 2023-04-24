package com.sviatkuzbyt.mafia.data.game.repositories

import android.content.Context
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.elements.*

sealed class ColorRole {
    object Red : ColorRole()
    object Black : ColorRole()
    object Lift : ColorRole()
}

class PlayerPanelRepository(
    private val gameArray: Array<PlayerData>,
    private val context: Context
) {

    private var redRoles = 0
    private var blackRoles = 0
    private var liftRole = 0

    fun getPlayerPanelList(): MutableList<PlayerPanelData> {
        val playerPanelList = mutableListOf<PlayerPanelData>()
        gameArray.forEach {
            if(it.isAlive){
                val dataAboutRole = getDataAboutRole(it.role)
                setColorRole(dataAboutRole.color, 1)
                playerPanelList.add(fillItem(it, dataAboutRole))
            }
        }
        return playerPanelList
    }

    private fun getDataAboutRole(roleType: Int) = when(roleType){
        PEACEFUL -> DataAboutRole(R.drawable.people_ic, ColorRole.Red)
        MAFIA -> DataAboutRole(R.drawable.gun_ic, ColorRole.Black)
        COMMISSAR -> DataAboutRole(R.drawable.hat_ic, ColorRole.Red)
        DOCTOR -> DataAboutRole(R.drawable.plus_ic, ColorRole.Red)
        DON -> DataAboutRole(R.drawable.red_hat_ic, ColorRole.Black)
        PUTANA -> DataAboutRole(R.drawable.lips_ic, ColorRole.Red)
        LIFT -> DataAboutRole(R.drawable.knife_ic, ColorRole.Lift)
        else -> DataAboutRole(R.drawable.bird_ic, ColorRole.Red)
    }

    private fun setColorRole(role: ColorRole, count: Int) {
        when(role){
            ColorRole.Red -> redRoles += count
            ColorRole.Black -> blackRoles += count
            else -> liftRole += count
        }
    }

    private fun fillItem(playerData: PlayerData, dataAboutRole: DataAboutRole) =
        PlayerPanelData(
            playerData.id,
            playerData.name,
            dataAboutRole.icon,
            playerData.role,
            dataAboutRole.color,
            playerData.roleName
        )

    fun editPlayer(player: PlayerPanelData) {
        val isAlive = !player.isSelected
        val count = if(isAlive) 1 else -1
        gameArray[player.id].isAlive = isAlive
        setColorRole(player.color, count)
    }

    fun matchResult(): String? {
        return when {
            blackRoles >= redRoles -> makeResultText(ColorRole.Black)
            blackRoles + liftRole == 0 -> makeResultText(ColorRole.Red)
            blackRoles + redRoles <= liftRole -> makeResultText(ColorRole.Lift)
            else -> null
        }
    }

    private fun makeResultText(color: ColorRole) =
        "${context.getString(makeWinnerString(color))}\n\n${makeRolesString()}"

    private fun makeRolesString(): String{
        var roleList = ""
        gameArray.forEach {roleList += "${it.name} - ${it.roleName}\n"}
        return roleList
    }

    private fun makeWinnerString(color: ColorRole): Int{
        return when(color){
            ColorRole.Red -> R.string.peaceful_win
            ColorRole.Black -> R.string.mafia_win
            else -> R.string.lift_win
        }
    }
}