package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

public final class Orientation implements GameFacadeComponent {

    private SensorManager sensorManager;
    private Sensor sensor;
    private OrientationState orientationState;

    public Orientation(@NonNull GameFacade gameFacade) {
        this.orientationState = new OrientationState(gameFacade);
    }

    public OrientationSnapshot snapshot() {
        return this.orientationState.relativeSnapshot();
    }

    public void create(@NonNull Context context) {
        this.sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    public void resume() {
        this.sensorManager.registerListener(this.orientationState, this.sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause() {
        this.sensorManager.unregisterListener(this.orientationState);
        this.orientationState.reset();
    }

    public void destroy() {
        this.sensorManager = null;
        this.sensor = null;
    }

}
