package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

public final class Touch implements GameFacadeComponent, View.OnTouchListener {

    private GameFacade gameFacade;

    private boolean isTouched = false;
    private MutableVector2D touchPosition = Vector2D.fromNothing();
    private long touchStartTime = 0;

    public Touch(@NonNull GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    public synchronized TouchEvent get() {
        if (this.isTouched) {
            long time = Time.now() - this.touchStartTime;
            return new TouchEvent(this.gameFacade, this.touchPosition, Timer.convertToFloatTime(time));
        }
        return null;
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
        this.isTouched = false;
    }

    @Override
    public synchronized boolean onTouch(@NonNull View view, @NonNull MotionEvent event) {
        Vector2D centeredViewVector = Vector2D.fromCartesian(
                event.getX() - view.getWidth() / 2,
                event.getY() - view.getHeight() / 2
            );

        if (event.getAction() == MotionEvent.ACTION_DOWN)
            return this.handleTouchDown(centeredViewVector);
        else if (event.getAction() == MotionEvent.ACTION_MOVE)
            return this.handleTouchMove(centeredViewVector);
        else if (event.getAction() == MotionEvent.ACTION_UP)
            return this.handleTouchUp(centeredViewVector);

        return false;
    }

    private boolean handleTouchDown(@NonNull Vector2D position) {
        this.isTouched = true;
        this.touchPosition = Vector2D.from(position);
        this.touchStartTime = Time.now();
        return true;
    }

    private boolean handleTouchMove(@NonNull Vector2D position) {
        this.touchPosition.setTo(position);
        return true;
    }

    private boolean handleTouchUp(@NonNull Vector2D position) {
        this.isTouched = false;
        this.touchPosition.setTo(position);
        return true;
    }

}
