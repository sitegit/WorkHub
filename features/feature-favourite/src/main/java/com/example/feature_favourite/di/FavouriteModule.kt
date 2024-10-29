package com.example.feature_favourite.di

import androidx.lifecycle.ViewModel
import com.example.core_ui.di.ViewModelKey
import com.example.feature_favourite.data.FavouriteRepositoryImpl
import com.example.feature_favourite.domain.FavouriteRepository
import com.example.feature_favourite.presentation.FavouriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface FavouriteModule {

    @Binds
    @Singleton
    fun bindFavouriteFeatureRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    fun bindFavouriteViewModel(viewModel: FavouriteViewModel): ViewModel
}
