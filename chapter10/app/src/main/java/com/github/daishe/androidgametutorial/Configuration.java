package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;

public final class Configuration implements GameFacadeComponent {

    public long framesPerSecond = 30;
    public boolean unboundFrameRate = false;

    public float deviceRotationLimit = (float)Math.PI / 8;

    public float minVisibleRange = 540f;
    public float minSpawnRange = 1100f;
    public float spawnSpaceRange = 400f;
    public float killingRange = 1500f;

    public float clearHearingRange = 300f;
    public float decayingHearingSpaceRange = 400f;
    public int maxSimultaneousSoundStreams = 20;

    public float playerShipMaxVelocity = 100f;
    public float playerShipMaxAcceleration = 75f;
    public int numberOfPlayerShipTrails = 4;
    public float playerShipTrailOffset = 50f;
    public float playerShipTrailScaleFactor = 0.5f;
    public float playerShipTrailAlphaFactor = 0.25f;

    public float rocketMaxVelocity = 300f;
    public float rocketMaxAcceleration = 500f;
    public float rocketFireTimeout = 2f;

    public long asteroidsLimit = 100;
    public float asteroidMaxVelocity = 80f;

    public boolean drawFramesPerSecond = true;
    public float framesPerSecondCalcInterval = 0.1f;
    public boolean drawPhysicsDebugInfo = true;

    public Configuration() {
    }

    @Override
    public void create(@NonNull Context context) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
    }

}
