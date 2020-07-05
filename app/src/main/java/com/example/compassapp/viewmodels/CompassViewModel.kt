package com.example.compassapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.compassapp.data.CompassRepository
import com.example.compassapp.data.models.CompassOrientation
import com.example.compassapp.data.models.Coordinate
import com.example.compassapp.data.models.LocationModel
import com.example.compassapp.data.models.OrientationModel
import io.reactivex.Flowable
import io.reactivex.Single

class CompassViewModel(val repo: CompassRepository) : ViewModel() {

//    var chosenLatitude = LiveData<>

    fun getOrientationModel(): Flowable<OrientationModel> {
        return repo.orientation.map { constructOrientationModel(it) }
    }

    fun getCurrentLocationModel(): Flowable<LocationModel> {
        return repo.locationUpdates.map { constructLocationModel(it) }
    }

    fun getDestinationLocationModel(): Single<LocationModel> {
        return repo.destinationLocation.map { constructLocationModel(it) }
    }

    fun setDestinationLocation(coordinate: Coordinate){
        repo.updateDestinationPosition(coordinate)
    }

    private fun constructOrientationModel(compassOrientation: CompassOrientation): OrientationModel {
        return OrientationModel(compassOrientation)
    }

    private fun constructLocationModel(coordinate: Coordinate): LocationModel {
        return LocationModel(coordinate)
    }
}