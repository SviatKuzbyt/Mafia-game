package com.sviatkuzbyt.mafia.data.game.repositories

import android.content.Context
import com.sviatkuzbyt.mafia.R
import com.sviatkuzbyt.mafia.data.game.elements.*

class SettingGameRepository(private val context: Context, private val autoPlayers: Boolean) {

    val fileManager = FileManager(context)
    var rolesArray = arrayOf<Roles>()
    var playersList = mutableListOf<String>()

    init {
        readList()
    }

    fun getBasicRolesArray() = rolesArray
    fun getBasicPlayersList() = playersList

    fun getGameList(_rolesArray: Array<Roles>, _playerList: List<String>): Array<PlayerData> {
        val roles = _rolesArray.flatMap { role -> List(role.count) { role.roleType } }.shuffled()
        return Array(_playerList.size) {
            val role = roles[it]
            PlayerData(it, _playerList[it], role, getRoleName(role))
        }
    }

    private fun getRoleName(roleType: Int): String{
        val roleId = when(roleType){
            PEACEFUL -> R.string.peaceful
            MAFIA -> R.string.mafia
            COMMISSAR -> R.string.commissar
            DOCTOR -> R.string.doctor
            DON -> R.string.don
            PUTANA -> R.string.putana
            LIFT -> R.string.lift
            else -> R.string.immortal
        }
        return context.getString(roleId)
    }

    private fun initStandardLists(){
        rolesArray = arrayOf(
            Roles(context.getString(R.string.mafia), R.drawable.gun_ic, 1, 5, MAFIA),
            Roles(context.getString(R.string.commissar), R.drawable.hat_ic, 1, 1, COMMISSAR),
            Roles(context.getString(R.string.doctor), R.drawable.plus_ic, 0, 1, DOCTOR),
            Roles(context.getString(R.string.peaceful), R.drawable.people_ic, 3, 10, PEACEFUL),
            Roles(context.getString(R.string.putana), R.drawable.lips_ic, 0, 1, PUTANA),
            Roles(context.getString(R.string.don), R.drawable.red_hat_ic, 0, 1, DON),
            Roles(context.getString(R.string.lift), R.drawable.knife_ic, 0, 1, LIFT),
            Roles(context.getString(R.string.immortal), R.drawable.bird_ic, 0, 1, IMMORTAL)
        )

        val playersCount = rolesArray.sumOf { it.count }
        playersList = if(autoPlayers)
            MutableList(playersCount) { "${context.getString(R.string.player)} ${it + 1}" }
        else
            MutableList(playersCount) {""}
    }

    private fun readList(){
        val _rolesArray = fileManager.readRoleArray()
        val _playersList = fileManager.readPlayerList()
        if(_rolesArray != null && _playersList != null){
            rolesArray = _rolesArray
            playersList = _playersList
        } else
            initStandardLists()
    }

    fun saveData(_rolesArray: Array<Roles>, _playersList: MutableList<String>){
        fileManager.writeFile(_rolesArray, "roleArray")
        fileManager.writeFile(_playersList, "playerList")
    }
}