package com.example.compassapp.data.models

class CompassOrientation (var destinationDirection:Float, var lastDestinationDirection:Float, var polesDirection: Float, var lastPolesDirection:Float) {
    constructor() : this(0f,0f,0f,0f)
}