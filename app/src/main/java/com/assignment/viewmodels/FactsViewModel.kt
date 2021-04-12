package com.assignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import com.assignment.room.DatabaseHelper
import com.assignment.room.FactsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

/**
 * @author Harpreet Singh
 */

class FactsViewModel (private val dashboardRepository: FactsRepository,private val dbHelper : DatabaseHelper) : ViewModel() {
    lateinit var factsList: LiveData<MutableList<FactsModel>>

    //fetching data from api and emit the response with the sealed class cases
    fun factsApi() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dashboardRepository.factsApi()))
        } catch (exception: IllegalStateException) {
            emit(Resource.Failure(true, null,null))
        }
    }

    //fetching all the saved facts in the local database
    fun fetchFacts() {
        viewModelScope.launch {
            try {
                factsList = dbHelper.getFacts()
            } catch (e: IllegalStateException) {
                // handler error

            }
        }
    }

    //insert all updated values in the local database
    fun insert(facts: List<FactsModel>) {
        viewModelScope.launch {
            try {
            dbHelper.insertAll(facts)
            } catch (e: IllegalStateException) {
                // handler error
            }
        }
    }

    // it will clear all the saved data in the local database
    fun clearAll() = viewModelScope.launch {
        dbHelper.deleteAll()
    }
}