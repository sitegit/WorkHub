package com.example.feature_main.di

import com.example.feature_main.presenation.MainFragment
import dagger.Component

@Component(
    modules = [MainModule::class],
    dependencies = [MainFeatureComponentDependencies::class]
)
interface MainFeatureComponent {

    fun inject(mainFragment: MainFragment)
}