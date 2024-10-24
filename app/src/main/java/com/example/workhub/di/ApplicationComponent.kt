package com.example.workhub.di

import android.content.Context
import com.example.core_network.di.NetworkModule
import com.example.feature_main.di.MainFeatureComponentDependencies
import com.example.feature_main.di.MainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, ViewModelModule::class, MainModule::class]
)
interface ApplicationComponent : MainFeatureComponentDependencies {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}