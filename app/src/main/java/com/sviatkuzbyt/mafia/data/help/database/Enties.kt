package com.sviatkuzbyt.mafia.data.help.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ukrainian(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val text: String
)

@Entity
data class English(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val text: String
)