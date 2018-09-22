package com.github.daishe.androidgametutorial;

import android.support.annotation.NonNull;

public class MutableVector2D extends Vector2D {

    public MutableVector2D() { super(); }
    public MutableVector2D(@NonNull Vector2D other) { super(other); }

    public final float x(float x) { return setMutableVector2DUsingCartesian(x, this.y(), this).x(); }
    public final float y(float y) { return setMutableVector2DUsingCartesian(this.x(), y, this).y(); }

    public final float radius(float radius) { return setMutableVector2DUsingPolar(radius, this.theta(), this).radius(); }
    public final float radiusSquare(float radiusSquare) { return setMutableVector2DUsingPolar((float)Math.sqrt(radiusSquare), this.theta(), this).radiusSquare(); }
    public final float theta(float theta) { return setMutableVector2DUsingPolar(this.radius(), theta, this).theta(); }

    public final float abs(float abs) { return setMutableVector2DUsingPolar(abs, this.theta(), this).abs(); }
    public final float absSquare(float absSquare) { return setMutableVector2DUsingPolar((float)Math.sqrt(absSquare), this.theta(), this).absSquare(); }

    @NonNull public static MutableVector2D fromNothing() { return new MutableVector2D(); }
    @NonNull public static MutableVector2D from(@NonNull Vector2D other) { return new MutableVector2D(other); }
    @NonNull public static MutableVector2D fromVector(@NonNull Vector2D other) { return new MutableVector2D(other); }
    @NonNull public static MutableVector2D fromCartesian(float x, float y) { return setMutableVector2DUsingCartesian(x, y, new MutableVector2D()); }
    @NonNull public static MutableVector2D fromPolar(float radius, float theta) { return setMutableVector2DUsingPolar(radius, theta, new MutableVector2D()); }

    @NonNull public MutableVector2D setTo(@NonNull Vector2D other) { return setMutableVector2DUsingCartesian(other.x(), other.y(), this); }
    @NonNull public MutableVector2D setToCartesian(float x, float y) { return setMutableVector2DUsingCartesian(x, y, this); }
    @NonNull public MutableVector2D setToPolar(float radius, float theta) { return setMutableVector2DUsingPolar(radius, theta, this); }

    @NonNull public final MutableVector2D addInPlace(@NonNull Vector2D other) { return setMutableVector2DUsingCartesian(this.x() + other.x(), this.y() + other.y(), this); }
    @NonNull public final MutableVector2D subInPlace(@NonNull Vector2D other) { return setMutableVector2DUsingCartesian(this.x() - other.x(), this.y() - other.y(), this); }
    @NonNull public final MutableVector2D mulInPlace(float by) { return setMutableVector2DUsingCartesian(this.x() * by, this.y() * by, this); }
    @NonNull public final MutableVector2D divInPlace(float by) { return setMutableVector2DUsingCartesian(this.x() / by, this.y() / by, this); }

    @NonNull public final MutableVector2D normalizeInPlace() { return setMutableVector2DUsingPolar(1.f, this.theta(), this); }

    @NonNull
    public final MutableVector2D limitInPlace(float to) {
        float radiusSquare = this.radiusSquare();
        if (radiusSquare > to * to) {
            float ratio = to / (float)Math.sqrt(radiusSquare);
            return setMutableVector2DUsingCartesian(this.x() * ratio, this.y() * ratio, this);
        }
        return this;
    }

    public boolean equals(Object object) {
        if (object instanceof MutableVector2D) {
            Vector2D other = (Vector2D)object;
            return this.x() == other.x() && this.y() == other.y();
        }
        return super.equals(object);
    }

    @NonNull
    static MutableVector2D setMutableVector2DUsingCartesian(float x, float y, @NonNull MutableVector2D vector) {
        setVector2DUsingCartesian(x, y, vector);
        return vector;
    }

    @NonNull
    static MutableVector2D setMutableVector2DUsingPolar(float radius, float theta, @NonNull MutableVector2D vector) {
        setVector2DUsingPolar(radius, theta, vector);
        return vector;
    }

}
