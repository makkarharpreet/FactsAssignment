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

class FactsViewModel (private val dashboardRepository: FactsRepository,private val dbHelper : DatabaseHelper) : ViewModel() {
    lateinit var factsList: LiveData<MutableList<FactsModel>>

    fun factsApi() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dashboardRepository.factsApi()))
        } catch (exception: Exception) {
            emit(Resource.Failure(true, null,null))
        }
    }

    fun fetchFacts() {
        viewModelScope.launch {
            try {
                factsList = dbHelper.getFacts()
            } catch (e: Exception) {
                // handler error
            }
        }
    }

    fun insert(facts: List<FactsModel>) {
        viewModelScope.launch {
            try {
            dbHelper.insertAll(facts)
            } catch (e: Exception) {
                // handler error
            }
        }
    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = dbHelper.deleteAll()
    }
}