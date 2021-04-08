package com.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import kotlinx.coroutines.Dispatchers

class FactsViewModel (private val dashboardRepository: FactsRepository) : ViewModel() {

    fun factsApi(url: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(dashboardRepository.factsApi(url)))
        } catch (exception: Exception) {
            emit(Resource.Failure(true, null,null))
        }
    }
}