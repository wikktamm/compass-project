package com.example.compassapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.compassapp.R
import com.example.compassapp.viewmodels.CompassViewModel
import com.example.compassapp.viewmodels.CompassViewModelFactory

class CompassActivity : AppCompatActivity() {

    lateinit var viewModel : CompassViewModel
    private val viewModelFactory = CompassViewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        if (checkForPermissions()) {
            viewModel = ViewModelProvider(this, viewModelFactory).get(CompassViewModel::class.java)
            displayCompassFragment()
        }
        else requestPermissions()
    }

    private fun displayCompassFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, CompassFragment()).commit()
    }

    private fun checkForPermissions() =
        isFineLocationGranted() && isCoarseLocationGranted()


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 0
        )
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
