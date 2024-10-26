package com.example.feature_main.presenation.main

import com.example.feature_main.domain.entity.Response

sealed class MainUiState {
    data object Initial : MainUiState()
    data object Loading : MainUiState()
    data class Success(val data: Response) : MainUiState()
    data class Error(val message: String) : MainUiState()
}