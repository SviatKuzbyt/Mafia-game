package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import android.util.Log
import com.sviatkuzbyt.mafia.R

class SettingGameRepository(private val context: Context) {

    private val rolesArray = arrayOf(
        Roles(context.getString(R.string.mafia), R.drawable.gun_ic, 1, 5, RoleType.Mafia),
        Roles(context.getString(R.string.commissioner), R.drawable.hat_ic, 1, 1, RoleType.Commissar),
        Roles(context.getString(R.string.doctor), R.drawable.plus_ic, 0, 1, RoleType.Doctor),
        Roles(context.getString(R.string.peaceful), R.drawable.people_icon, 3, 10, RoleType.Peaceful),
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

            PlayerData(
                _playerList[it],
                role,
                true
            )
        }
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