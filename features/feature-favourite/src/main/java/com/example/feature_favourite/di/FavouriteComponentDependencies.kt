package com.example.feature_favourite.di

import android.content.Context
import com.example.core_ui.utils.ViewModelFactory
import com.example.feature_favourite.domain.FavouriteRepository

interface FavouriteComponentDependencies {

    fun getFavouriteRepository(): FavouriteRepository

    fun getViewModelFactory(): ViewModelFactory

    fun getContext(): Context
}