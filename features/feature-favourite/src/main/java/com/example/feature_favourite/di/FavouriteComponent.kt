package com.example.feature_favourite.di

import com.example.feature_favourite.presentation.FavouriteFragment
import dagger.Component

@Component(
    dependencies = [FavouriteComponentDependencies::class]
)
interface FavouriteComponent {

    fun inject(favouriteFragment: FavouriteFragment)
}