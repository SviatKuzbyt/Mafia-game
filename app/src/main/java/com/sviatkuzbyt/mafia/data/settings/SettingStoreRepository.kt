package com.sviatkuzbyt.mafia.data.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.sviatkuzbyt.mafia.ui.main.settingsStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

open class SettingStoreRepository(private val context: Context) {
    private val autoPlayer = booleanPreferencesKey("auto_player")
    private val autoWin = booleanPreferencesKey("auto_win")
    private val roleLanguage = intPreferencesKey("role_language")

    suspend fun getRoleLanguage() = context.settingsStore.data.map {
        it[roleLanguage] ?: 0
    }.first()

    suspend fun getAutoPlayer() = getBooleanParam(autoPlayer)

    suspend fun getAutoWin() = getBooleanParam(autoWin)

    private suspend fun getBooleanParam(key: Preferences.Key<Boolean>) =
        context.settingsStore.data.map {
        it[key] ?: true
    }.first()

    suspend fun setRoleLanguage(param: Int){
        context.settingsStore.edit {
            it[roleLanguage] = param
        }
    }

    suspend fun setAutoPlayer(param: Boolean){
        setBooleanParam(param, autoPlayer)
    }

    suspend fun setAutoWin(param: Boolean){
        setBooleanParam(param, autoWin)
    }

    private suspend fun setBooleanParam(param: Boolean, key: Preferences.Key<Boolean>){
        context.settingsStore.edit {
            it[key] = param
        }
    }
}