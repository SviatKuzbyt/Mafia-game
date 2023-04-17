package com.sviatkuzbyt.mafia.data.game.repositories

import android.content.Context
import com.sviatkuzbyt.mafia.data.game.elements.FileManager
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData

class GameRepository(context: Context) {
    private val fileManager = FileManager(context)
    fun readArray() = fileManager.readGameArray()


    fun writeArray(gameArray: Array<PlayerData>){
        fileManager.writeFile(gameArray, "arrayGame")
    }
}

