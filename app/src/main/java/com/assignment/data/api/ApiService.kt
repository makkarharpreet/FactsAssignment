package com.assignment.data.api

import com.assignment.model.FactsResponseModel
import retrofit2.http.GET
    import retrofit2.http.Url

interface ApiService {
    @GET("facts.json")
    suspend fun getFacts():FactsResponseModel
}