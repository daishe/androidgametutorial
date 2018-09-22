package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;

public final class Configuration implements GameFacadeComponent {

    public long framesPerSecond = 30;
    public boolean unboundFrameRate = false;

    public float minVisibleRange = 540f;
    public float minSpawnRange = 1000f;
    public float spawnSpaceRange = 200f;
    public float killingRange = 1200f;

    public long asteroidsLimit = 15;
    public float asteroidMaxVelocity = 180f;

    public float rocketMaxVelocity = 300f;

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
