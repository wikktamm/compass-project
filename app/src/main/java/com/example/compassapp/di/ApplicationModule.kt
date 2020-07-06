package com.example.compassapp.di

import com.example.compassapp.data.orientation.CompassOrientationSource
import com.example.compassapp.data.orientation.OrientationSource
import com.example.compassapp.logic.RxSensors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideOrientationSource(rxSensors: RxSensors): OrientationSource =
        CompassOrientationSource(rxSensors)
}

