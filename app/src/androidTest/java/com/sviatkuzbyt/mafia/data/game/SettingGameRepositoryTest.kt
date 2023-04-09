package com.sviatkuzbyt.mafia.data.game

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test


internal class SettingGameRepositoryTest{
    lateinit var settingGameRepository: SettingGameRepository

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        settingGameRepository = SettingGameRepository(appContext)
    }

    @Test
    fun checkGeneralWork(){
        settingGameRepository.changeRoleCount(0, 1)
        settingGameRepository.playersList[2] = "Sviat"

        println(settingGameRepository.rolesArray.toList())
        println(settingGameRepository.playersCount)
        println(settingGameRepository.playersList)

        settingGameRepository.changeRoleCount(3, -1)

        println(settingGameRepository.rolesArray.toList())
        println(settingGameRepository.playersCount)
        println(settingGameRepository.playersList)
    }
}