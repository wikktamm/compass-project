package com.example.compassapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compassapp.data.CompassRepository
import com.example.compassapp.data.models.CompassOrientation
import com.example.compassapp.data.models.Coordinate
import com.example.compassapp.data.models.OrientationModel
import io.reactivex.Flowable

class CompassViewModel(private val repo: CompassRepository) : ViewModel() {

    private val _chosenLatitudeAndLongitude =  MutableLiveData<Pair<Float,Float>>()
    var chosenLatitudeAndLongitude : LiveData<Pair<Float, Float>> = _chosenLatitudeAndLongitude

    fun getOrientationModel(): Flowable<OrientationModel> {
        return repo.orientation.map { constructOrientationModel(it) }
    }

    fun setDestinationLocation(coordinate: Coordinate){
        repo.updateDestinationPosition(coordinate)
    }

    private fun constructOrientationModel(compassOrientation: CompassOrientation): OrientationModel {
        return OrientationModel(compassOrientation)
    }

    fun saveChosenCoordinates(latitude:Float, longitude:Float) {
        _chosenLatitudeAndLongitude.postValue(Pair(latitude, longitude))
    }
}