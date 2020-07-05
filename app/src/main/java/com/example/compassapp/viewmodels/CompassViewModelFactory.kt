package com.example.compassapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compassapp.data.CompassRepository

class CompassViewModelFactory(private val repo:CompassRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompassViewModel(repo) as T
    }
}