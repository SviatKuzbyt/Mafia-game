package com.sviatkuzbyt.mafia.data.help.database

import android.content.Context
import com.sviatkuzbyt.mafia.data.help.HelpItemData
import com.sviatkuzbyt.mafia.data.help.HelpListData
import java.util.Locale

class HelpDBRepository(context: Context) {

    private val language = Locale.getDefault().language
    private val helpDao = HelpDataBase.getInstance(context).dao()

    fun getHelpList(): Array<HelpListData>{
        return if(language == "uk") helpDao.getHelpListUkrainian()
        else helpDao.getHelpListEnglish()
    }

    fun getHelpItem(id: Int): HelpItemData {
        return if(language == "uk") helpDao.getHelpItemUkrainian(id)
        else helpDao.getHelpItemEnglish(id)
    }
}