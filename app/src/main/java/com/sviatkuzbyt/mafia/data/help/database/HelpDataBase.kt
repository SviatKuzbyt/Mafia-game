package com.sviatkuzbyt.mafia.data.help.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Ukrainian::class, English::class])
abstract class HelpDataBase: RoomDatabase() {
    abstract fun dao(): HelpDao

    companion object {
        @Volatile private var instance: HelpDataBase? = null
        fun getInstance(context: Context): HelpDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): HelpDataBase {
            return Room.databaseBuilder(context, HelpDataBase::class.java, "help-db")
                .createFromAsset("MafiaHelp.db")
                .build()
        }
    }
}