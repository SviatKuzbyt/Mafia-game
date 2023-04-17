package com.sviatkuzbyt.mafia.data.game.repositories

import android.content.Context
import android.graphics.drawable.Drawable
import com.sviatkuzbyt.mafia.data.game.elements.CardRole
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.data.game.elements.loadImage

class RolesRepository(private val context: Context) {
    fun generateCardRolesArray(gameArray: Array<PlayerData>): Array<CardRole>{
        return Array(gameArray.size){
            CardRole(
                gameArray[it].name,
                gameArray[it].roleName,
                loadImage(gameArray[it].role, context),
            )
        }
    }
}