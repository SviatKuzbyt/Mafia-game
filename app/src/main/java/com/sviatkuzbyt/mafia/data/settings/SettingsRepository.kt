package com.sviatkuzbyt.mafia.data.settings

import android.content.Context
import java.io.File

class SettingsRepository(private val context: Context): SettingStoreRepository(context) {
    fun clearData(){
        var file = File(context.filesDir, "arrayGame.txt")
        file.delete()
        file = File(context.filesDir, "roleArray.txt")
        file.delete()
    }
}