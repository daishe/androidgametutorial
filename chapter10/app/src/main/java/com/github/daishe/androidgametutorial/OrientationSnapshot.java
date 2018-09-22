package com.github.daishe.androidgametutorial;

import android.support.annotation.NonNull;

public class OrientationSnapshot {

    private GameFacade gameFacade;

    private final float yaw;
    private final float pitch;
    private final float roll;

    public OrientationSnapshot(@NonNull GameFacade gameFacade, float yaw, float pitch, float roll) {
        this.gameFacade = gameFacade;

        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public float yaw() {
        return this.yaw;
    }

    public float pitch() {
        return this.pitch;
    }

    public float roll() {
        return this.roll;
    }

    @NonNull
    public Vector2D asInGameVector2D() {
        float deviceRotationLimit = this.gameFacade.configuration().deviceRotationLimit;
        return Vector2D.fromCartesian(this.roll, -this.pitch)
            .limitInPlace(deviceRotationLimit)
            .divInPlace(deviceRotationLimit);
    }

}
