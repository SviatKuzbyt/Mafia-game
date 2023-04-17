package com.sviatkuzbyt.mafia.data.help.database

import androidx.room.Dao
import androidx.room.Query
import com.sviatkuzbyt.mafia.data.help.HelpItemData
import com.sviatkuzbyt.mafia.data.help.HelpListData

@Dao
interface HelpDao {

    @Query("SELECT id, name FROM Ukrainian")
    fun getHelpListUkrainian(): Array<HelpListData>

    @Query("SELECT name, text FROM Ukrainian where id=:id LIMIT 1")
    fun getHelpItemUkrainian(id: Int): HelpItemData

    @Query("SELECT id, name FROM English")
    fun getHelpListEnglish(): Array<HelpListData>

    @Query("SELECT name, text FROM English where id=:id LIMIT 1")
    fun getHelpItemEnglish(id: Int): HelpItemData
}