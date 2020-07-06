package com.example.compassapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.compassapp.R
import com.example.compassapp.data.CompassRepository
import com.example.compassapp.data.location.CompassLocationSource
import com.example.compassapp.data.orientation.CompassOrientationSource
import com.example.compassapp.logic.RxSensors
import com.example.compassapp.utils.Constants.PERMISSION_FINE_COARSE
import com.example.compassapp.viewmodels.CompassViewModel
import com.example.compassapp.viewmodels.CompassViewModelFactory
import com.patloew.rxlocation.RxLocation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompassActivity : AppCompatActivity() {

    val viewModel: CompassViewModel by viewModels()

    @Inject
    lateinit var viewModelFactory: CompassViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(CompassViewModel::class.java)
        if (checkForPermissions()) {
            displayCompassFragment()
        } else requestPermissions()
    }

    private fun displayCompassFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, CompassFragment())
            .commit()
    }

    private fun checkForPermissions() =
        isFineLocationGranted() && isCoarseLocationGranted()


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSION_FINE_COARSE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_FINE_COARSE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayCompassFragment()
            } else requestPermissions()
        }
    }

    private fun isFineLocationGranted() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    private fun isCoarseLocationGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
