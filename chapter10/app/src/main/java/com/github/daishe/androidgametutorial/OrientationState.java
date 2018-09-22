package com.github.daishe.androidgametutorial;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class OrientationState implements SensorEventListener {

    private GameFacade gameFacade;

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private AtomicFloat initialYaw = new AtomicFloat();
    private AtomicFloat initialPitch = new AtomicFloat();
    private AtomicFloat initialRoll = new AtomicFloat();

    private AtomicFloat currentYaw = new AtomicFloat();
    private AtomicFloat currentPitch = new AtomicFloat();
    private AtomicFloat currentRoll = new AtomicFloat();

    public OrientationState(@NonNull GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    public OrientationSnapshot relativeSnapshot() {
        return this.makeSnapshot(
                this.currentYaw.get() - this.initialYaw.get(),
                this.currentPitch.get() - this.initialPitch.get(),
                this.currentRoll.get() - this.initialRoll.get()
            );
    }

    public OrientationSnapshot initialStateSnapshot() {
        return this.makeSnapshot(this.initialYaw.get(), this.initialPitch.get(), this.initialRoll.get());
    }

    public OrientationSnapshot currentStateSnapshot() {
        return this.makeSnapshot(this.currentYaw.get(), this.currentPitch.get(), this.currentRoll.get());
    }

    public boolean ready() {
        return this.initialized.get();
    }

    public void reset() {
        this.initialized.set(false);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing to do
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            float[] orientation = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);
            this.setSensorOutput(orientation[0], orientation[1], orientation[2]);
        }
    }

    private void setSensorOutput(float yaw, float pitch, float roll) {
        yaw = (!Float.isNaN(yaw)) ? yaw : 0;
        pitch = (!Float.isNaN(pitch)) ? pitch : 0;
        roll = (!Float.isNaN(roll)) ? roll : 0;

        if (!this.initialized.get() && (yaw != 0 || pitch != 0 || roll != 0)) {
            this.initialYaw.set(yaw);
            this.initialPitch.set(pitch);
            this.initialRoll.set(roll);
            this.initialized.set(true);
        }
        this.currentYaw.set(yaw);
        this.currentPitch.set(pitch);
        this.currentRoll.set(roll);
    }

    private OrientationSnapshot makeSnapshot(float yaw, float pitch, float roll) {
        return new OrientationSnapshot(this.gameFacade, yaw, pitch, roll);
    }

}
