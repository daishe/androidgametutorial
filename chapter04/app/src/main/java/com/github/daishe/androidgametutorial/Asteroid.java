package com.github.daishe.androidgametutorial;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Asteroid {

    private Paint paint = new Paint();

    private Vector2D centerPosition;
    private MutableVector2D position = Vector2D.fromNothing();
    private MutableVector2D velocity = Vector2D.fromNothing();

    private Bitmap asteroidImage;

    public Asteroid(Bitmap asteroidImage, Vector2D centerPosition, Vector2D initialPosition, Vector2D initialVelocity) {
        this.paint.setAntiAlias(true);

        this.centerPosition = Vector2D.from(centerPosition);
        this.position.setTo(initialPosition);
        this.velocity.setTo(initialVelocity);

        this.asteroidImage = asteroidImage;
    }

    public Vector2D position() {
        return this.position;
    }

    public Vector2D position(Vector2D position) {
        return this.position.setTo(position);
    }

    public void update(float timeShift) {
        Vector2D acceleration = Vector2D.from(this.centerPosition)
            .subInPlace(this.position);
        this.velocity.addInPlace(acceleration.mul(timeShift));
        this.position.addInPlace(this.velocity.mul(timeShift));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.asteroidImage, this.position.x(), this.position.y(), this.paint);
    }

}
