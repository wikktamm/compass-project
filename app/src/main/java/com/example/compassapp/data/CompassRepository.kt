package com.example.compassapp.data

import com.example.compassapp.data.location.LocationSource
import com.example.compassapp.data.models.CompassOrientation
import com.example.compassapp.data.models.Coordinate
import com.example.compassapp.data.orientation.OrientationSource
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CompassRepository @Inject constructor(
    private val orientationSource: OrientationSource,
    private val locationSource: LocationSource
) : OrientationSource,
    LocationSource {

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

    override fun getLocationUpdates(): Flowable<Coordinate> {
        return locationSource.locationUpdates
            .flatMap { coordinate ->
                updateCurrentLocation(coordinate)
                Flowable.just(coordinate)
            }
    }
}