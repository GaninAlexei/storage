package com.example.storageversion2

import android.content.Context
import java.io.*

class InternalStorage(private val activity: MainActivity){

    companion object{
        private const val FILE_NAME = "FILE_NAME"
    }

    fun write(text: String){
        lateinit var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fileOutputStream.write(text.toByteArray())
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
        finally {
            fileOutputStream.close()
        }
    }

    fun read() : String?{
        lateinit var fileInputStream: FileInputStream
        return try {
            fileInputStream = activity.openFileInput(FILE_NAME)
            val read = fileInputStream.readBytes()
            read.decodeToString()
        } catch (ex: Exception){
            ex.printStackTrace()
            null
        } finally {
            fileInputStream.close()
        }
    }
}