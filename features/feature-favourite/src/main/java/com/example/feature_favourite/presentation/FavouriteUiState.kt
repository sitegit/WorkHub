package com.example.feature_favourite.presentation

import com.example.feature_favourite.domain.Vacancy

sealed class FavouriteUiState {
    data object Initial : FavouriteUiState()
    data object Loading : FavouriteUiState()
    data class Success(val data: List<Vacancy>) : FavouriteUiState()
    data class Error(val message: String) : FavouriteUiState()
}