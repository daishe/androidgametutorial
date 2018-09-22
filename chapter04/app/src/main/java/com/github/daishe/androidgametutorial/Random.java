package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;

public final class Random implements GameFacadeComponent {

    private java.util.Random randomImpl = null;

    public Random() {
    }

    public int integer() {
        return this.randomImpl.nextInt();
    }

    public int integer(int bound) {
        return this.randomImpl.nextInt(bound);
    }

    public float linear() {
        return this.randomImpl.nextFloat();
    }

    public float linear(float scale) {
        return this.linear() * scale;
    }

    public float linear(float min, float max) {
        return this.linear(max - min) + min;
    }

    public float gaussian() {
        return (float)this.randomImpl.nextGaussian();
    }

    public float gaussian(float limitTo) {
        limitTo = Math.abs(limitTo);
        while (true) {
            float result = this.gaussian();
            if (result >= -limitTo && result <= limitTo)
                return result;
        }
    }

    public float angle() {
        return this.linear(0f, (float)Math.PI * 2);
    }

    public float sign() {
        return this.randomImpl.nextBoolean() ? 1f : -1f;
    }

    @Override
    public void create(@NonNull Context context) {
        this.randomImpl = new java.util.Random();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
        this.randomImpl = null;
    }

}
