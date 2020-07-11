package com.example.compassapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.compassapp.R
import com.example.compassapp.utils.areCoordinatesValuesCorrect
import com.example.compassapp.utils.getFloatValue
import com.example.compassapp.utils.showErrorToastLong
import com.example.compassapp.viewmodels.CompassViewModel
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_update_direction.*

@AndroidEntryPoint
class UpdateDirectionDialog : DialogFragment() {

    private lateinit var viewModel: CompassViewModel
    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_update_direction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CompassActivity).viewModel
        setListeners()
        restoreData()
    }

    private fun setListeners() {
        disposable = btShowDirection.clicks().subscribe {
            if (areCoordinatesValuesCorrect(
                    etLatitude.text.toString(),
                    etLongitude.text.toString()
                )
            ) {
                val latitude = etLatitude.getFloatValue()
                val longitude = etLongitude.getFloatValue()
                viewModel.saveChosenCoordinates(latitude, longitude)
                dismiss()
            } else {
                showErrorToastLong(getString(R.string.invalid_input))
            }
        }

        btClose.setOnClickListener { dismiss() }
    }

    private fun restoreData() {
        val coordinates = viewModel.chosenLatitudeAndLongitude.value
        coordinates?.let {
            etLatitude.setText(it.first.toString())
            etLongitude.setText(it.second.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}