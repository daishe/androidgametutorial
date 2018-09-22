package com.github.daishe.androidgametutorial;

import android.support.annotation.NonNull;

public class Vector2D {

    private float x;
    private float y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(@NonNull Vector2D other) {
        this.x = other.x();
        this.y = other.y();
    }

    public final float x() { return this.x; }
    public final float y() { return this.y; }

    public final float radius() { return cartesianToPolarRadius(this.x(), this.y()); }
    public final float radiusSquare() { return cartesianToPolarRadiusSquare(this.x(), this.y()); }
    public final float theta() { return cartesianToPolarTheta(this.x(), this.y()); }

    public final float abs() { return cartesianToPolarRadius(this.x(), this.y()); }
    public final float absSquare() { return cartesianToPolarRadiusSquare(this.x(), this.y()); }

    @NonNull public static MutableVector2D fromNothing() { return MutableVector2D.fromNothing(); }
    @NonNull public static MutableVector2D from(@NonNull Vector2D other) { return MutableVector2D.from(other); }
    @NonNull public static MutableVector2D fromVector(@NonNull Vector2D other) { return MutableVector2D.fromVector(other); }
    @NonNull public static MutableVector2D fromCartesian(float x, float y) { return MutableVector2D.fromCartesian(x, y); }
    @NonNull public static MutableVector2D fromPolar(float radius, float theta) { return MutableVector2D.fromPolar(radius, theta); }

    @NonNull public final MutableVector2D withCartesian(float x, float y) { return fromCartesian(x, y); }
    @NonNull public final MutableVector2D withCartesianX(float x) { return fromCartesian(x, this.y()); }
    @NonNull public final MutableVector2D withCartesianY(float y) { return fromCartesian(this.x(), y); }
    @NonNull public final MutableVector2D withPolar(float radius, float theta) { return fromPolar(radius, theta); }
    @NonNull public final MutableVector2D withPolarRadius(float radius) { return fromPolar(radius, this.theta()); }
    @NonNull public final MutableVector2D withPolarTheta(float theta) { return fromPolar(this.radius(), theta); }

    @NonNull public final MutableVector2D copy() { return fromCartesian(this.x(), this.y()); }

    @NonNull public final Vector2D asImmutable() { return this; }

    @NonNull public final MutableVector2D add(@NonNull Vector2D other) { return fromCartesian(this.x() + other.x(), this.y() + other.y()); }
    @NonNull public final MutableVector2D sub(@NonNull Vector2D other) { return fromCartesian(this.x() - other.x(), this.y() - other.y()); }
    @NonNull public final MutableVector2D mul(float by) { return fromCartesian(this.x() * by, this.y() * by); }
    @NonNull public final MutableVector2D div(float by) { return fromCartesian(this.x() / by, this.y() / by); }

    @NonNull public final MutableVector2D normalize() { return fromPolar(1.f, this.theta()); }

    @NonNull
    public final MutableVector2D limit(float to) {
        float radiusSquare = this.radiusSquare();
        if (radiusSquare > to * to) {
            float ratio = to / (float)Math.sqrt(radiusSquare);
            return fromCartesian(this.x() * ratio, this.y() * ratio);
        }
        return this.copy();
    }

    public boolean equals(float radius) { return this.abs() == radius; }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object instanceof Float)
            return this.abs() == (Float)object;
        if (object instanceof Vector2D) {
            Vector2D other = (Vector2D)object;
            return this.x() == other.x() && this.y() == other.y();
        }
        return super.equals(object);
    }

    @NonNull
    public final String stringify() {
        return "[" + String.valueOf(this.x()) + "," + String.valueOf(this.y()) + "]";
    }

    @NonNull
    public final String stringifyExtended() {
        return "[x:" + String.valueOf(this.x()) + ", y:" + String.valueOf(this.y()) + ", radius:" + String.valueOf(this.radius()) + ", theta:" + String.valueOf(this.theta()) + "]";
    }

    static float cartesianToPolarRadius(float x, float y) {return (float)Math.sqrt(x*x + y*y); }
    static float cartesianToPolarRadiusSquare(float x, float y) { return x*x + y*y; }
    static float cartesianToPolarTheta(float x, float y) { return (float)Math.atan2(y, x); }
    static float polarToCartesianX(float radius, float theta) { return radius * (float)Math.cos(theta); }
    static float polarToCartesianY(float radius, float theta) { return radius * (float)Math.sin(theta); }

    @NonNull
    static Vector2D setVector2DUsingCartesian(float x, float y, @NonNull Vector2D vector) {
        vector.x = x;
        vector.y = y;
        return vector;
    }

    @NonNull
    static Vector2D setVector2DUsingPolar(float radius, float theta, @NonNull Vector2D vector) {
        vector.x = polarToCartesianX(radius, theta);
        vector.y = polarToCartesianY(radius, theta);
        return vector;
    }

}
