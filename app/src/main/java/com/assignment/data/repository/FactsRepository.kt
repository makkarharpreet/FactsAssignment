package com.assignment.data.repository

import com.assignment.data.api.ApiService

/**
 * @author Harpreet Singh
 */
class FactsRepository (private val apiService: ApiService): BaseRepository() {

    suspend fun factsApi() = apiService.getFacts()

}