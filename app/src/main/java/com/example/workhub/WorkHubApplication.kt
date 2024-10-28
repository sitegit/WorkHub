package com.example.workhub

import android.app.Application
import com.example.feature_favourite.di.FavouriteComponentDependencies
import com.example.feature_favourite.di.FavouriteComponentDependenciesProvider
import com.example.feature_main.di.MainComponentDependencies
import com.example.feature_main.di.MainComponentDependenciesProvider
import com.example.workhub.di.ApplicationComponent
import com.example.workhub.di.DaggerApplicationComponent

class WorkHubApplication :
    Application(),
    MainComponentDependenciesProvider,
    FavouriteComponentDependenciesProvider
{
    private val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getMainFeatureComponentDependencies(): MainComponentDependencies = appComponent

    override fun getFavouriteComponentDependencies(): FavouriteComponentDependencies = appComponent
}