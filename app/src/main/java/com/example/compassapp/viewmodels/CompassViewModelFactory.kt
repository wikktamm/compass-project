package com.example.compassapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compassapp.data.CompassRepository
import javax.inject.Inject

class CompassViewModelFactory @Inject constructor(private val repo:CompassRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompassViewModel(repo) as T
    }
}