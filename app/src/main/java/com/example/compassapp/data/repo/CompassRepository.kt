package com.example.compassapp.data.repo

import com.example.compassapp.data.models.CompassOrientation
import com.example.compassapp.data.models.Coordinate
import com.example.compassapp.data.orientation.OrientationSource
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompassRepository @Inject constructor(
    private val orientationSource: OrientationSource
) : OrientationSource
{

    override fun updateCurrentLocation(position: Coordinate?) {
        orientationSource.updateCurrentLocation(position)
    }

    override fun getOrientation(): Flowable<CompassOrientation> {
        return orientationSource.orientation
    }

    override fun updateDestinationPosition(destinationPosition: Coordinate?) {
        orientationSource.updateDestinationPosition(destinationPosition)
    }

    override fun getDestinationLocation(): Single<Coordinate> {
        return orientationSource.destinationLocation
    }
}