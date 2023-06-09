package com.sviatkuzbyt.mafia.data.game.elements

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.util.Log
import com.caverock.androidsvg.SVG
import java.util.*


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

        val inputStream = context.assets.open("basic_cards/$roleName.svg").use {
            SVG.getFromInputStream(it).renderToPicture()
        }
        PictureDrawable(inputStream)
    } catch (e: Exception){
        null
    }
}

fun Context.getLocaleStringResource(
    lang: Int,
    resourceId: Int,
): String {
    val locale = when (lang) {
        1 -> Locale("uk", "UA")
        2 -> Locale("en", "US")
        else -> Locale.getDefault()
    }

    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    return createConfigurationContext(config).getText(resourceId).toString()
}

val MAFIA = 0
val COMMISSAR = 1
val DOCTOR = 2
val PEACEFUL = 3
val PUTANA = 4
val DON = 5
val LIFT = 6
val IMMORTAL = 7