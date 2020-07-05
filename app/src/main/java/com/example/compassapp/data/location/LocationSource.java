package com.example.compassapp.data.location;

import com.example.compassapp.data.models.Coordinate;

import io.reactivex.Flowable;

public interface LocationSource {
    Flowable<Coordinate> getLocationUpdates();
}
