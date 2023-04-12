package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.caverock.androidsvg.SVG
import com.sviatkuzbyt.mafia.R

class RolesRepository(private val context: Context) {
    fun generateCardRolesArray(gameArray: Array<PlayerData>): Array<CardRole>{
        return Array(gameArray.size){
            CardRole(
                gameArray[it].name,
                getRoleName(gameArray[it].role),
                loadImage(gameArray[it].role)
            )
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