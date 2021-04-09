package com.assignment.data.network

import okhttp3.ResponseBody
/**
 * @author Harpreet Singh
 * this sealed class is used to return cases depending upon the response
 * we will get from api service.
 */

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
     class Success<out T>(val value : T) : Resource<T>()
     class Failure  (
        val isNetworkError: Boolean,
        val errorCode : Int?,
        val errorBody : ResponseBody?
    ): Resource<Nothing>()
}