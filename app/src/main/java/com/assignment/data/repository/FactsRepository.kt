package com.assignment.data.repository

import com.assignment.data.api.ApiService

class FactsRepository (private val apiService: ApiService): BaseRepository() {

    suspend fun factsApi(url: String) = apiService.getFacts(url)

}