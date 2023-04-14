package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import android.util.Log
import com.sviatkuzbyt.mafia.R

class SettingGameRepository(private val context: Context) {

    private val rolesArray = arrayOf(
        Roles(context.getString(R.string.mafia), R.drawable.gun_ic, 1, 5, RoleType.Mafia),
        Roles(context.getString(R.string.commissar), R.drawable.hat_ic, 1, 1, RoleType.Commissar),
        Roles(context.getString(R.string.doctor), R.drawable.plus_ic, 0, 1, RoleType.Doctor),
        Roles(context.getString(R.string.peaceful), R.drawable.people_ic, 3, 10, RoleType.Peaceful),
        Roles(context.getString(R.string.putana), R.drawable.lips_ic, 0, 1, RoleType.Putana),
        Roles(context.getString(R.string.don), R.drawable.red_hat_ic, 0, 1, RoleType.Don),
        Roles(context.getString(R.string.lift), R.drawable.knife_ic, 0, 1, RoleType.Lift),
        Roles(context.getString(R.string.immortal), R.drawable.bird_ic, 0, 1, RoleType.Immortal)
    )

    private var playersCount = rolesArray.sumOf { it.count }
    private val playersList = MutableList(playersCount) { "${context.getString(R.string.player)} ${it + 1}" }

    fun getBasicRolesArray() = rolesArray
    fun getBasicPlayersList() = playersList

    fun getGameList(_rolesArray: Array<Roles>, _playerList: List<String>): Array<PlayerData>{
        val roles = mutableListOf<RoleType>()
        _rolesArray.forEach {
            if (it.count > 0){
                for (i in 1..it.count){
                    roles.add(it.roleType)
                    Log.v("mainButtonClick", it.roleType.toString())
                }
            }
        }

        return Array(_playerList.size){
            val role = roles.random()
            roles.remove(role)
            PlayerData(it, _playerList[it], role, getRoleName(role))
        }
    }

    private fun getRoleName(roleType: RoleType): String{
        val roleId = when(roleType){
            RoleType.Peaceful -> R.string.peaceful
            RoleType.Mafia -> R.string.mafia
            RoleType.Commissar -> R.string.commissar
            RoleType.Doctor -> R.string.doctor
            RoleType.Don -> R.string.don
            RoleType.Putana -> R.string.putana
            RoleType.Lift -> R.string.lift
            RoleType.Immortal -> R.string.immortal
        }
        return context.getString(roleId)
    }
}

data class Roles(
    val name: String,
    val icon: Int,
    val min: Int,
    val max: Int,
    val roleType: RoleType,
    var count: Int = min
)