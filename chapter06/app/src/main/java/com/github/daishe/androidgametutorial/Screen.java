package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public final class Screen implements GameFacadeComponent {

    private static final float minVisibleRange = 540f;

    private View view = null;

    public Screen() {
    }

    @NonNull
    public Vector2D physicalDimensions() {
        if (this.view != null)
            return Vector2D.fromCartesian(this.view.getWidth(), this.view.getHeight());
        return Vector2D.fromNothing();
    }

    @NonNull
    public Vector2D dimensions() {
        return this.physicalToGame(this.physicalDimensions());
    }

    public float scale() {
        Vector2D dimensions = this.physicalDimensions();
        float minDimension = Math.min(dimensions.x(), dimensions.y());
        return minDimension / 2 / Screen.minVisibleRange;
    }

    @NonNull
    public Vector2D physicalToGame(Vector2D physicalVector2D) {
        return Vector2D.from(physicalVector2D).divInPlace(this.scale());
    }

    @NonNull
    public Vector2D gameToPhysical(Vector2D gameVector2D) {
        return Vector2D.from(gameVector2D).mulInPlace(this.scale());
    }

    public void setPhysicalDimensionsSource(View view) {
        this.view = view;
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
        this.view = null;
    }

}
