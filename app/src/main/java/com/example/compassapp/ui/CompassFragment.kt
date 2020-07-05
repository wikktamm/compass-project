package com.example.compassapp.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import com.example.compassapp.R
import com.example.compassapp.data.models.OrientationModel
import com.example.compassapp.logic.CompassContract
import com.example.compassapp.utils.SensorUnavailableException
import com.example.compassapp.utils.showErrorToastLong
import com.example.compassapp.viewmodels.CompassViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_compass.*

class CompassFragment : Fragment(R.layout.fragment_compass), CompassContract {

    private var disposable = CompositeDisposable()

    private lateinit var viewModel: CompassViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CompassActivity).viewModel
        setListeners()
    }

    private fun setListeners() {
        btCoordinatesDialog.setOnClickListener {
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
    }


    override fun showOnErrorGettingDirections() {
        showErrorToastLong(getString(R.string.error_loading_direction))
    }

    override fun showOnErrorGettingLocation() {
        showErrorToastLong(getString(R.string.error_loading_location))
    }

    private fun openDirectionsDialog() {
        UpdateDirectionDialog().show(activity!!.supportFragmentManager, "dialog_directions")
    }

    private fun adjustArrow(azimuth: Float, currentAzimuth: Float, targetView: View) {
        val animation = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )

        animation.duration = 300
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