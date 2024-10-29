package com.example.feature_favourite.domain

sealed class DbResponse<out T> {

    data class Success<out T>(val data: T): DbResponse<T>()

    data class Error(val message: String) : DbResponse<Nothing>()

}