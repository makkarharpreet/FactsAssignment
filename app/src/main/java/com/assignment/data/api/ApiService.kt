package com.assignment.data.api

import com.assignment.model.FactsResponseModel
import retrofit2.http.GET

/**
 * @author Harpreet Singh
 */
interface ApiService {
    @GET("facts.json")
    suspend fun getFacts():FactsResponseModel
}