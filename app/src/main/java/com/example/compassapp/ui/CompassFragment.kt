package com.example.compassapp.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.compassapp.R
import com.example.compassapp.data.models.Coordinate
import com.example.compassapp.data.models.OrientationModel
import com.example.compassapp.logic.CompassContract
import com.example.compassapp.utils.Constants.DELAY_ADJUST_ARROW_POSITION
import com.example.compassapp.utils.Constants.TAG_DIALOG_UPDATE_COORDINATES
import com.example.compassapp.utils.showErrorToastLong
import com.example.compassapp.viewmodels.CompassViewModel
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_compass.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class CompassFragment : Fragment(R.layout.fragment_compass), CompassContract {

    private var disposable = CompositeDisposable()

    private lateinit var viewModel: CompassViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CompassActivity).viewModel
        setListeners()
    }

    private fun setListeners() {
        btCoordinatesDialog.clicks().throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe {
            openDirectionsDialog()
        }
    }

    private fun bindViewModel() {
        disposable.add(
            viewModel.getOrientationModel()
                .subscribe(
                    { uiModel -> updateDirections(uiModel) },
                    { showOnErrorGettingDirections() })
        )
        viewModel.chosenLatitudeAndLongitude.observe(viewLifecycleOwner, Observer { coordinates ->
            updateDestinationLocation(coordinates.first, coordinates.second)
        })
    }

    private fun unbindViewModel() {
        disposable.clear()
    }

    override fun updateDirections(orientationModel: OrientationModel) {
        val orientation = orientationModel.orientation
        adjustArrow(orientation.polesDirection, orientation.lastPolesDirection, ivCompass)
        adjustArrow(
            orientation.destinationDirection,
            orientation.lastDestinationDirection,
            ivDestinationArrow
        )
        tvAzimuth.text = "${orientation.lastPolesDirection.roundToInt()}°"
    }

    private fun updateDestinationLocation(latitude: Float, longitude: Float) {
        viewModel.setDestinationLocation(
            Coordinate(
                latitude,
                longitude
            )
        )
    }

    override fun showOnErrorGettingDirections() {
        showErrorToastLong(getString(R.string.error_loading_direction))
    }

    override fun showOnErrorGettingLocation() {
        showErrorToastLong(getString(R.string.error_loading_location))
    }

    private fun openDirectionsDialog() {
        UpdateDirectionDialog().show(
            requireActivity().supportFragmentManager,
            TAG_DIALOG_UPDATE_COORDINATES
        )
    }

    private fun adjustArrow(azimuth: Float, currentAzimuth: Float, targetView: View) {
        val animation = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )

        animation.duration = DELAY_ADJUST_ARROW_POSITION
        animation.repeatCount = 0
        animation.fillAfter = true
        targetView.startAnimation(animation)
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun onPause() {
        super.onPause()
        unbindViewModel()
    }
}

