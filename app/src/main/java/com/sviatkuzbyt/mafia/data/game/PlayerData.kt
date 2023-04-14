package com.sviatkuzbyt.mafia.data.game

data class PlayerData(
    val id: Int,
    val name: String,
    val role: RoleType,
    val roleName: String,
    var isAlive: Boolean = true
)



