package com.sviatkuzbyt.mafia.data.game.elements

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.caverock.androidsvg.SVG

fun loadImage(roleType: Int, context: Context): Drawable?{
    return try {
        val roleName = when(roleType){
            PEACEFUL -> "peaceful"
            MAFIA -> "mafia"
            COMMISSAR -> "commissar"
            DOCTOR -> "doctor"
            DON -> "don"
            PUTANA -> "putana"
            LIFT -> "lift"
            else -> "immortal"
        }

        val inputStream = context.assets.open("basic_cards/$roleName.svg")
        val svgDrawable = SVG.getFromInputStream(inputStream).renderToPicture()
        inputStream.close()
        PictureDrawable(svgDrawable)
    } catch (e: Exception){
        null
    }
}

val MAFIA = 0
val COMMISSAR = 1
val DOCTOR = 2
val PEACEFUL = 3
val PUTANA = 4
val DON = 5
val LIFT = 6
val IMMORTAL = 7