package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;

public final class Timer implements GameFacadeComponent {

    private long startTime;
    private long subTime;

    private boolean paused;
    private long pauseTime;

    public Timer() {
    }

    public synchronized long timeAsLong() {
        if (this.paused)
            return this.pauseTime;
        long delta = Time.now() - this.startTime - this.subTime;
        return (delta > 0) ? delta : 0;
    }

    public synchronized float timeAsFloat() {
        return Timer.convertToFloatTime(this.timeAsLong());
    }

    @Override
    public void create(@NonNull Context context) {
        this.startTime = Time.now();
        this.subTime = 0;
        this.paused = false;
        this.pauseTime = 0;

        this.pause();
    }

    @Override
    public synchronized void pause() {
        this.pauseTime = this.timeAsLong();
        this.paused = true;
    }

    @Override
    public synchronized void resume() {
        this.paused = false;
        this.subTime += this.timeAsLong() - this.pauseTime;
    }

    @Override
    public void destroy() {
    }

    public static long convertToLongTime(float time) {
        double doubleResolution = (double)Time.resolution();
        double doubleTime = (double)time;
        return (long)(doubleTime * doubleResolution);
    }

    public static float convertToFloatTime(long time) {
        double doubleResolution = (double)Time.resolution();
        double doubleTime = (double)time;
        return (float)(doubleTime / doubleResolution);
    }

}
