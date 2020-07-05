package com.example.compassapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.compassapp.R
import com.example.compassapp.viewmodels.CompassViewModel
import io.reactivex.disposables.CompositeDisposable

class CompassFragment : Fragment(R.layout.fragment_compass) {

    private var disposable  = CompositeDisposable()

    private lateinit var viewModel: CompassViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CompassActivity).viewModel
    }
}