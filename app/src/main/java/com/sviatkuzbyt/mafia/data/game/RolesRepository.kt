package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.caverock.androidsvg.SVG

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

data class CardRole(
    val player: String,
    val role: String,
    val image: Drawable?
)