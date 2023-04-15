package com.sviatkuzbyt.mafia.data.game

import android.content.Context
import android.util.Log
import java.io.*

class FileManager(private val context: Context) {

     fun writeFile(list: Any, name: String){
         try {
             val file = File(context.filesDir, "$name.txt")

             val outputStream = ObjectOutputStream(file.outputStream())
             outputStream.writeObject(list)
             outputStream.close()
         } catch (e: Exception){
             Log.v("test app", e.message.toString())
         }

    }

     fun readGameArray(): Array<PlayerData>? {
        return try{
            val file = File(context.filesDir, "arrayGame.txt")

            val inputStream = ObjectInputStream(file.inputStream())
            val array = inputStream.readObject() as Array<PlayerData>
            inputStream.close()
            array
        } catch (e: Exception){
            null
        }
    }

     fun readRoleArray(): Array<Roles>? {
        return try{
            val file = File(context.filesDir, "roleArray.txt")

            val inputStream = ObjectInputStream(file.inputStream())
            val array = inputStream.readObject() as Array<Roles>
            inputStream.close()
            array
        } catch (e: Exception){
            null
        }
    }

     fun readPlayerList(): MutableList<String>? {
        return try{
            val file = File(context.filesDir, "playerList.txt")

            val inputStream = ObjectInputStream(file.inputStream())
            val array = inputStream.readObject() as MutableList<String>
            inputStream.close()
            array
        } catch (e: Exception){
            null
        }
    }
}