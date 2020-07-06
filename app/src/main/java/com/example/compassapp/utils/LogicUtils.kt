package com.example.compassapp.utils

fun areCoordinatesValuesCorrect(eLatitude: String, longitude: String): Boolean {
    if (eLatitude.isEmpty() || longitude.isEmpty()) return false
    if (90 < eLatitude.toFloat() || eLatitude.toFloat() < -90) return false
    if (180 < longitude.toFloat() || longitude.toFloat() < -180) return false
    return true
}
