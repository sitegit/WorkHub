package com.example.workhub

import android.app.Application
import com.example.feature_main.di.MainFeatureComponentDependencies
import com.example.feature_main.di.MainFeatureComponentDependenciesProvider
import com.example.workhub.di.ApplicationComponent
import com.example.workhub.di.DaggerApplicationComponent

class WorkHubApplication :
    Application(),
    MainFeatureComponentDependenciesProvider
{
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getMainFeatureComponentDependencies(): MainFeatureComponentDependencies {
        return appComponent
    }
}