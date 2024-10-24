package com.example.feature_main.domain

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T): ApiResponse<T>()

    data class Error(val message: String) : ApiResponse<Nothing>()

}