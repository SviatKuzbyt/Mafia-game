package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import com.sviatkuzbyt.mafia.R

sealed class ColorRole{
    object Red: ColorRole()
    object Black: ColorRole()
    object Lift: ColorRole()
}

class PlayerPanelRepository(private val gameArray: Array<PlayerData>, private val context: Context) {

    private var redRoles = 0
    private var blackRoles = 0
    private var liftRole = 0

    fun getPlayerPanelList(): MutableList<PlayerPanelData>{
        val playerPanelList = mutableListOf<PlayerPanelData>()
        gameArray.forEach {
            if(it.isAlive){
                val dataAboutRole = getDataAboutRole(it.role)
                setColorRole(dataAboutRole.color, 1)
                playerPanelList.add(PlayerPanelData(
                    it.id,
                    it.name,
                    dataAboutRole.icon,
                    it.role,
                    dataAboutRole.color,
                    it.roleName
                ))
            }
        }
        return playerPanelList
    }

    private fun getDataAboutRole(roleType: Int): DataAboutRole = when(roleType){
        PEACEFUL -> DataAboutRole(R.drawable.people_ic, ColorRole.Red)
        MAFIA -> DataAboutRole(R.drawable.gun_ic, ColorRole.Black)
        COMMISSAR -> DataAboutRole(R.drawable.hat_ic, ColorRole.Red)
        DOCTOR -> DataAboutRole(R.drawable.plus_ic, ColorRole.Red)
        DON -> DataAboutRole(R.drawable.red_hat_ic, ColorRole.Black)
        PUTANA -> DataAboutRole(R.drawable.lips_ic, ColorRole.Red)
        LIFT -> DataAboutRole(R.drawable.knife_ic, ColorRole.Lift)
        else -> DataAboutRole(R.drawable.bird_ic, ColorRole.Red)
    }

    private fun setColorRole(role: ColorRole, count: Int){
        when(role){
            ColorRole.Red -> redRoles += count
            ColorRole.Black -> blackRoles += count
            ColorRole.Lift -> liftRole += count
        }
    }

    fun editPlayer(player: PlayerPanelData){
        val isAlive = !player.isSelected
        val count = if(isAlive) 1 else -1
        gameArray[player.id].isAlive = isAlive
        setColorRole(player.color, count)
    }

    fun matchResult(): String?{
        return if(blackRoles >= redRoles)
            makeResultText(ColorRole.Black)
        else if(blackRoles + liftRole == 0)
            makeResultText(ColorRole.Red)
        else if(blackRoles + redRoles <= liftRole)
            makeResultText(ColorRole.Lift)
        else null
    }

    fun makeResultText(color: ColorRole): String{
        val role = when(color){
            ColorRole.Red -> R.string.peaceful_win
            ColorRole.Black -> R.string.mafia_win
            ColorRole.Lift -> R.string.lift_win
        }

        var roleList = ""
        gameArray.forEach {
            roleList += "${it.name} - ${it.roleName}\n"
        }

        return "${context.getString(role)}\n\n$roleList"
    }


}

data class DataAboutRole(
    val icon: Int,
    val color: ColorRole
)

data class PlayerPanelData(
    val id: Int,
    val name: String,
    val icon: Int,
    val typeRole: Int,
    val color: ColorRole,
    val roleName: String,
    var isSelected:Boolean = false,
)