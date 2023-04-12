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
    fun checkGenerateGameList(){
        val rolesList = settingGameRepository.getBasicRolesArray()
        val playerList = settingGameRepository.getBasicPlayersList()
        val resultList = settingGameRepository.getGameList(rolesList, playerList)

        var resultOutput = ""
        resultList.forEach {
            resultOutput += it.toString()
        }

        println(resultOutput)
    }
}