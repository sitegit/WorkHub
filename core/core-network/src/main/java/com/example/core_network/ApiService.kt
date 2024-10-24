package com.example.core_network

import com.example.core_network.model.ResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("mock_vacancies")
    suspend fun getMockData(): ResponseDto
}