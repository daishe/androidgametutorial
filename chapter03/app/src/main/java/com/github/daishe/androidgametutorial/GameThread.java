package com.github.daishe.androidgametutorial;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameThread extends Thread {

    private static final int framesPerSecond = 30;

    private GameView view;

    private AtomicBoolean isDrawing = new AtomicBoolean(false);

    public GameThread(@NonNull GameView view) {
        this.view = view;
    }

    public boolean drawing() {
        return this.isDrawing.get();
    }

    public boolean drawing(boolean isDrawing) {
        this.isDrawing.set(isDrawing);
        return isDrawing;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                long frameLogicStartTimePoint = Time.now();

                if (this.drawing()) {
                    this.executeUpdate();
                    this.executeDraw();
                }

                long frameLogicStopTimePoint = Time.now();

                long frameLogicTimeDelta = frameLogicStopTimePoint - frameLogicStartTimePoint;
                frameLogicTimeDelta = frameLogicTimeDelta > 0 ? frameLogicTimeDelta : 0;
                long sleepTime = (this.ticksPerFrame() - frameLogicTimeDelta) / (Time.resolution() / 1000);
                Thread.sleep(sleepTime > 0 ? sleepTime : 0);
            }
            catch (InterruptedException e) {
                break;
            }
            catch (Exception e) {
            }
        }
    }

    private long ticksPerFrame() {
        double resolution = (double)Time.resolution();
        return Math.round(resolution / GameThread.framesPerSecond);
    }

    private void executeUpdate() {
        this.view.update();
    }

    private void executeDraw() {
        SurfaceHolder surfaceHolder = this.view.getHolder();
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null)
                this.view.onDraw(canvas);
        }
        finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

}
