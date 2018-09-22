package com.github.daishe.androidgametutorial;

import android.support.annotation.NonNull;

public class TouchEvent {

    private GameFacade gameFacade;

    private final Vector2D position;
    private final float time;

    public TouchEvent(@NonNull GameFacade gameFacade, @NonNull Vector2D position, float time) {
        this.gameFacade = gameFacade;

        this.position = Vector2D.from(position);
        this.time = time;
    }

    @NonNull
    public Vector2D physicalPosition() {
        return this.position;
    }

    @NonNull
    public Vector2D gamePosition() {
        return this.gameFacade.screen().physicalToGame(this.position);
    }

    public float time() {
        return this.time;
    }

}
