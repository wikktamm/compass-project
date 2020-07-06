package com.example.compassapp.di

import android.content.Context
import com.example.compassapp.data.orientation.CompassOrientationSource
import com.example.compassapp.data.orientation.OrientationSource
import com.example.compassapp.logic.RxSensors
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRxLocationClient(@ApplicationContext context: Context): RxLocation =
        RxLocation(context)

    @Singleton
    @Provides
    fun provideOrientationSource(rxSensors: RxSensors): OrientationSource =
        CompassOrientationSource(rxSensors)
}

