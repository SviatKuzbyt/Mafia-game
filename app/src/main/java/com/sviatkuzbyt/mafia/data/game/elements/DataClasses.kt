package com.sviatkuzbyt.mafia.data.game.elements

import android.graphics.drawable.Drawable
import com.sviatkuzbyt.mafia.data.game.repositories.ColorRole

data class PlayerData(
    val id: Int,
    val name: String,
    val role: Int,
    val roleName: String,
    var isAlive: Boolean = true
) : java.io.Serializable

data class Roles(
    val name: String,
    val icon: Int,
    val min: Int,
    val max: Int,
    val roleType: Int,
    var count: Int = min
): java.io.Serializable

data class DataAboutRole(
    val icon: Int,
    val color: ColorRole
)

data class PlayerPanelData(
    val id: Int,
    val name: String,
    val icon: Int,
    val typeRole: Int,
    val color: ColorRole,
    val roleName: String,
    var isSelected:Boolean = false,
)

data class CardRole(
    val player: String,
    val role: String,
    val image: Drawable?
)