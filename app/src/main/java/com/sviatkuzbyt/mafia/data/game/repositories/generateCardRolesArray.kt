package com.sviatkuzbyt.mafia.data.game.repositories

import android.content.Context
import com.sviatkuzbyt.mafia.data.game.elements.CardRole
import com.sviatkuzbyt.mafia.data.game.elements.PlayerData
import com.sviatkuzbyt.mafia.data.game.elements.loadImage
fun generateCardRolesArray(gameArray: Array<PlayerData>, context: Context): Array<CardRole>{
    return Array(gameArray.size){
        CardRole(
            gameArray[it].name,
            gameArray[it].roleName,
            loadImage(gameArray[it].role, context),
        )
    }
}