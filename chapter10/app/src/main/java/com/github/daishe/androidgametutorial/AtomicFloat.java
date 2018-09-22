package com.github.daishe.androidgametutorial;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFloat {

    private AtomicInteger data;

    public AtomicFloat() {
        this.data = new AtomicInteger(0);
    }

    public AtomicFloat(float value) {
        this.set(value);
    }

    public AtomicFloat(AtomicFloat value) {
        this.set(value);
    }

    public float get() {
        return Float.intBitsToFloat(this.data.get());
    }

    public void set(float value) {
        this.data.set(Float.floatToIntBits(value));
    }

    public void set(AtomicFloat value) {
        this.data.set(value.data.get());
    }

}
