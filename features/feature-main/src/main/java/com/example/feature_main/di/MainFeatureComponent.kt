package com.example.feature_main.di

import com.example.feature_main.presenation.main.MainFragment
import com.example.feature_main.presenation.relevant.RelevantVacanciesFragment
import dagger.Component

@Component(
    dependencies = [MainFeatureComponentDependencies::class]
)
interface MainFeatureComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(relevantVacanciesFragment: RelevantVacanciesFragment)
}