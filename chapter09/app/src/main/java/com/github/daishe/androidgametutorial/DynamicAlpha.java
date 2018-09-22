package com.github.daishe.androidgametutorial;

public class DynamicAlpha {

    private final float phaseTime;
    private float currentTime;

    private float initialAlpha;
    private float finalAlpha;
    private float currentAlpha;

    public DynamicAlpha(float initialAlpha, float finalAlpha, float phaseTime) {
        this.phaseTime = phaseTime;
        this.currentTime = 0;

        this.initialAlpha = initialAlpha;
        this.finalAlpha = finalAlpha;
        this.currentAlpha = initialAlpha;
    }

    public void update(float timeShift) {
        this.currentTime += timeShift;
        while (this.currentTime > this.phaseTime) {
            this.currentTime -= this.phaseTime;
            float alpha = this.initialAlpha;
            this.initialAlpha = this.finalAlpha;
            this.finalAlpha = alpha;
        }

        float mul = this.currentTime / this.phaseTime;
        this.currentAlpha = (this.finalAlpha - this.initialAlpha) * mul + this.initialAlpha;
    }

    public float get() {
        return this.currentAlpha;
    }

}
