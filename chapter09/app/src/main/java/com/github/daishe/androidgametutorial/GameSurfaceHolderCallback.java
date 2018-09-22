package com.github.daishe.androidgametutorial;

import android.view.SurfaceHolder;

public class GameSurfaceHolderCallback implements SurfaceHolder.Callback {

    private GameThread gameThread;

    public GameSurfaceHolderCallback(GameThread gameThread) {
        this.gameThread = gameThread;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.gameThread.drawing(false);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameThread.drawing(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

}
