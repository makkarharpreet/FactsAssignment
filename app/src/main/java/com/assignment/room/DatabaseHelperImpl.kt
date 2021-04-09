package com.assignment.room

import androidx.lifecycle.LiveData
import com.assignment.model.FactsResponseModel

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getFacts(): LiveData<MutableList<FactsModel>> = appDatabase.factsDao().getAll()

    override suspend fun insertAll(facts: List<FactsModel>) = appDatabase.factsDao().insertAll(facts)

    override suspend fun deleteAll()=appDatabase.factsDao().deleteAll()

}