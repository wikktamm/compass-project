package com.example.compassapp.logic;

import com.example.compassapp.data.models.OrientationModel;

public interface CompassContract {

    void updateDirections(OrientationModel orientation);

    void showOnErrorGettingDirections();
}
