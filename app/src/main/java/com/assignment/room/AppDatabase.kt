package com.assignment.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Harpreet Singh
 */

@Database(entities = [FactsModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun factsDao(): FactsDao

}