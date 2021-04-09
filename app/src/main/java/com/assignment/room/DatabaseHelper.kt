package com.assignment.room

import androidx.lifecycle.LiveData
import com.assignment.model.FactsResponseModel

interface DatabaseHelper {
    suspend fun getFacts(): LiveData<MutableList<FactsModel>>

    suspend fun insertAll(facts: List<FactsModel>)

    suspend fun deleteAll(): Int
}