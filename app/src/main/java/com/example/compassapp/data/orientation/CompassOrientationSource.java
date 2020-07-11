package com.example.compassapp.data.orientation;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.compassapp.data.models.CompassOrientation;
import com.example.compassapp.data.models.Coordinate;
import com.example.compassapp.logic.RxSensors;
import com.example.compassapp.utils.Constants;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScoped
public class CompassOrientationSource implements OrientationSource {
    private RxSensors rxSensorsClient;

    private Coordinate destinationCoordinate = Constants.INSTANCE.getSampleCoordinate();
    private Coordinate currentCoordinate = Constants.INSTANCE.getSampleCoordinate();

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float[] R = new float[9];
    private float[] I = new float[9];

    private float azimuth;
    private float lastPolesAzimuth;

    private float lastDestinationAzimuth;

    @Inject
    public CompassOrientationSource(RxSensors rxSensors) {
        this.rxSensorsClient = rxSensors;
    }

    @Override
    public Flowable<CompassOrientation> getOrientation(){
        return rxSensorsClient.observeSensors(Sensor.TYPE_ACCELEROMETER,
                Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_GAME)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(sensorEvent -> {

                    CompassOrientation compassOrientation = new CompassOrientation();

                    final float alpha = 0.97f;

                    synchronized (this) {
                        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                            mGravity[0] = alpha * mGravity[0] + (1 - alpha)
                                    * sensorEvent.values[0];
                            mGravity[1] = alpha * mGravity[1] + (1 - alpha)
                                    * sensorEvent.values[1];
                            mGravity[2] = alpha * mGravity[2] + (1 - alpha)
                                    * sensorEvent.values[2];
                        }

                        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                            mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
                                    * sensorEvent.values[0];
                            mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
                                    * sensorEvent.values[1];
                            mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
                                    * sensorEvent.values[2];

                        }

                        boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
                                mGeomagnetic);
                        if (success) {
                            float orientation[] = new float[3];
                            SensorManager.getOrientation(R, orientation);

                            azimuth = (float) Math.toDegrees(orientation[0]); // orientation
                            azimuth = (azimuth + 360) % 360;

                            compassOrientation.setPolesDirection(azimuth);
                            compassOrientation.setLastPolesDirection(lastPolesAzimuth);

                            // update last pole azimuth for next iteration
                            lastPolesAzimuth = azimuth;

                            double destinationAzimuth = azimuth -
                                    bearing(currentCoordinate.getLatitude(), currentCoordinate.getLongitude(),
                                            destinationCoordinate.getLatitude(), destinationCoordinate.getLongitude());

                            compassOrientation.setDestinationDirection((float) destinationAzimuth);
                            compassOrientation.setLastDestinationDirection(lastDestinationAzimuth);

                            // update last destination azimuth for next iteration
                            lastDestinationAzimuth = (float) destinationAzimuth;

                        }
                    }
                    return Flowable.just(compassOrientation);
                });
    }

    @Override
    public void updateCurrentLocation(Coordinate position) {
        this.currentCoordinate = position;
    }

    @Override
    public Single<Coordinate> getDestinationLocation() {
        return Single.just(destinationCoordinate);
    }

    @Override
    public void updateDestinationPosition(Coordinate destinationCoordinate) {
        this.destinationCoordinate = destinationCoordinate;
    }

    private double bearing(double startLat, double startLng, double endLat, double endLng) {
        double latitude1 = Math.toRadians(startLat);
        double latitude2 = Math.toRadians(endLat);
        double longDiff = Math.toRadians(endLng - startLng);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }
}
