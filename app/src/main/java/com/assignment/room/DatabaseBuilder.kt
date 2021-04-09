package com.assignment.room

import android.content.Context
import androidx.room.Room

/**
 * @author Harpreet Singh
 * this class is used to create database and get instance of the database
 */
object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "factsDatabase"
        ).allowMainThreadQueries().build()
}