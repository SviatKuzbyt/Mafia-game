package com.sviatkuzbyt.mafia.data.game

sealed class RoleType {
    object Mafia: RoleType()
    object Commissar: RoleType()
    object Doctor: RoleType()
    object Peaceful: RoleType()
}