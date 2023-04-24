package com.sviatkuzbyt.mafia.data.settings

import android.content.Context
import java.io.File

class SettingsRepository(private val context: Context): SettingStoreRepository(context) {
    fun clearData(){
        val fileArray = arrayOf(
            File(context.filesDir, "arrayGame.txt"),
            File(context.filesDir, "roleArray.txt"),
            File(context.filesDir, "playerList.txt")
        )
        fileArray.forEach { it.delete() }
    }
}