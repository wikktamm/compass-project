package com.example.compassapp.logic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Handler
import com.example.compassapp.utils.SensorUnavailableException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import java.util.*

class RxSensors constructor(context: Context){
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val sensors: List<Sensor>
        get() = sensorManager.getSensorList(Sensor.TYPE_ALL)

    fun hasSensor(sensorType: Int): Boolean {
        return sensorManager.getDefaultSensor(sensorType) != null
    }

    @JvmOverloads
    fun observeSensors(sensorType1: Int, sensorType2: Int, samplingPeriodInUs: Int,
                       handler: Handler? = null, strategy: BackpressureStrategy = BackpressureStrategy.BUFFER): Flowable<SensorEvent> {

        if (!hasSensor(sensorType1) || !hasSensor(sensorType2)) {
            val isFirstSensorPresent = hasSensor(sensorType1)
            val isSecondSensorPresent = hasSensor(sensorType2)
            val missingSensors = if(isFirstSensorPresent) "" else sensorType1.toString() + if(isSecondSensorPresent) "" else sensorType2.toString()
            val format = "Required sensors are not available on this device (sensors ids): %d"
            val message = String.format(Locale.getDefault(), format, missingSensors)
            return Flowable.error(SensorUnavailableException(message))
        }

        val sensor1 = sensorManager.getDefaultSensor(sensorType1)
        val sensor2 = sensorManager.getDefaultSensor(sensorType2)

        val wrapper = SensorEventListenerWrapper()
        val listener = wrapper.create()

        return Flowable.create(FlowableOnSubscribe<SensorEvent> { emitter ->
            wrapper.emitter = emitter

            if (handler == null) {
                sensorManager.registerListener(listener, sensor1, samplingPeriodInUs)
                sensorManager.registerListener(listener, sensor2, samplingPeriodInUs)
            } else {
                sensorManager.registerListener(listener, sensor1, samplingPeriodInUs, handler)
                sensorManager.registerListener(listener, sensor2, samplingPeriodInUs, handler)
            }
        }, strategy).doOnCancel { sensorManager.unregisterListener(listener) }
    }
}
