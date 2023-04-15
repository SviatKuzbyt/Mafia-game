package com.sviatkuzbyt.mafia.data.game

import android.content.Context

class GameRepository(context: Context) {
    private val fileManager = FileManager(context)
    fun readArray() = fileManager.readGameArray()


    fun writeArray(gameArray: Array<PlayerData>){
        fileManager.writeFile(gameArray, "arrayGame")
    }
}

data class PlayerData(
    val id: Int,
    val name: String,
    val role: Int,
    val roleName: String,
    var isAlive: Boolean = true
) : java.io.Serializable