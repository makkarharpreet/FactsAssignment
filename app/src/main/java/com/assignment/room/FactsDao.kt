package com.assignment.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.assignment.model.FactsResponseModel

@Dao
interface FactsDao {
    @Query("SELECT * FROM facts_table")
     fun getAll(): LiveData<MutableList<FactsModel>>

    @Insert
     fun insertAll(facts: List<FactsModel>)

    @Query("DELETE FROM facts_table")
    suspend fun deleteAll() : Int
}