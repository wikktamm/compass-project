package com.example.compassapp.data.orientation;

import com.example.compassapp.data.models.CompassOrientation;
import com.example.compassapp.data.models.Coordinate;

import io.reactivex.Flowable;
import io.reactivex.Single;


public interface OrientationSource {
    Flowable<CompassOrientation> getOrientation();

    Single<Coordinate> getDestinationLocation();

    void updateDestinationPosition(Coordinate destinationPosition);

    void updateCurrentLocation(Coordinate position);
}
