package com.sviatkuzbyt.mafia.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.sviatkuzbyt.mafia.ui.main.settingsStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

open class SettingStoreRepository(private val context: Context) {
    private val autoPlayer = booleanPreferencesKey("auto_player_off")
    private val autoWin = booleanPreferencesKey("auto_win_off")

    suspend fun getAutoPlayer() = context.settingsStore.data.map {
        it[autoPlayer] ?: true
    }.first()

    suspend fun getAutoWin() = context.settingsStore.data.map {
        it[autoWin] ?: true
    }.first()

    suspend fun setAutoPlayer(param: Boolean){
        context.settingsStore.edit {
            it[autoPlayer] = param
        }
    }

    suspend fun setAutoWin(param: Boolean){
        context.settingsStore.edit {
            it[autoWin] = param
        }
    }
}