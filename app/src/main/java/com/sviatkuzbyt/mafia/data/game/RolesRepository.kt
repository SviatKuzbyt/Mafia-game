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
                loadImage(gameArray[it].role)
            )
        }
    }

    private fun loadImage(roleType: RoleType): Drawable?{
        return try {
            val roleName = when(roleType){
                RoleType.Peaceful -> "peaceful"
                RoleType.Mafia -> "mafia"
                RoleType.Commissar -> "commissar"
                RoleType.Doctor -> "doctor"
                RoleType.Don -> "don"
                RoleType.Putana -> "putana"
                RoleType.Lift -> "lift"
                RoleType.Immortal -> "immortal"
            }

            val inputStream = context.assets.open("basic_cards/$roleName.svg")
            val svgDrawable = SVG.getFromInputStream(inputStream).renderToPicture()
            inputStream.close()
            PictureDrawable(svgDrawable)
        } catch (e: Exception){
            null
        }

    }
}

data class CardRole(
    val player: String,
    val role: String,
    val image: Drawable?
)