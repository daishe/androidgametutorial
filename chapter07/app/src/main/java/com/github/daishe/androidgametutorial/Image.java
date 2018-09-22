package com.github.daishe.androidgametutorial;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class Image {

    private Bitmap data = null;

    private float radius = 0;
    private float diagonal = 0;

    public Image() {
        this.reset();
    }

    public Image(@NonNull Bitmap from) {
        this.reset(from);
    }

    public Image(@NonNull Image from) {
        this.reset(from);
    }

    public float radius() {
        return this.radius;
    }

    public float diagonal() {
        return this.diagonal;
    }

    public void reset() {
        this.data = null;
        this.radius = 0;
        this.diagonal = 0;
    }

    public void reset(@NonNull Bitmap from) {
        this.data = from;

        float x = this.data.getWidth();
        float y = this.data.getHeight();
        this.radius = Math.max(x, y) / 2;
        this.diagonal = (float)Math.sqrt(x * x + y * y);
    }

    public void reset(@NonNull Image from) {
        this.data = from.data;
        this.radius = from.radius;
        this.diagonal = from.diagonal;
    }

    public void draw(GameCanvas gameCanvas, Vector2D at) {
        gameCanvas.drawBitmap(this.data, at);
    }

    public void draw(GameCanvas gameCanvas, Vector2D at, float scale, float rotation, float alpha) {
        gameCanvas.drawBitmap(this.data, at, scale, rotation, alpha);
    }

}
