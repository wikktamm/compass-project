package com.example.compassapp.data.location

import android.annotation.SuppressLint
import com.example.compassapp.data.models.Coordinate
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompassLocationSource(private val rxLocation: RxLocation)  : LocationSource {

    private
    val customLocationRequest: LocationRequest
        get() {
            val locationRequest = LocationRequest()
            locationRequest.interval = 2000
            locationRequest.fastestInterval = 2000
            locationRequest.smallestDisplacement = 10f
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            return locationRequest
        }

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(): Flowable<Coordinate> {
        return rxLocation.location().updates(customLocationRequest)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { position -> Observable.just(Coordinate(position.latitude, position.longitude)) }
            .toFlowable(BackpressureStrategy.LATEST)
    }
}