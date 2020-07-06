package com.example.compassapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.compassapp.R
import com.example.compassapp.viewmodels.CompassViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompassActivity : AppCompatActivity() {

    val viewModel: CompassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        displayCompassFragment(savedInstanceState == null)
    }

    private fun displayCompassFragment(shouldCreate: Boolean) {
        if (shouldCreate) {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, CompassFragment())
                .commit()
        }
    }
}
