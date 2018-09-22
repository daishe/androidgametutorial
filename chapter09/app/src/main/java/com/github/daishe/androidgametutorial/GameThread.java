package com.github.daishe.androidgametutorial;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameThread extends Thread {

    private WeakReference<Activity> activityWeakReference;
    private GameFacade gameFacade;
    private GameView view;

    private long lastFrameStartTimePoint = 0;
    private long lastFrameTimeDelta = 0;

    private long lastFPSCalcTimePoint = 0;
    private float fps = 0;

    private AtomicBoolean isDrawing = new AtomicBoolean(false);

    public GameThread(Activity activity, GameFacade gameFacade, GameView view) {
        this.activityWeakReference = new WeakReference<Activity>(activity);
        this.gameFacade = gameFacade;
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
    public synchronized void start() {
        long now = System.nanoTime();
        this.lastFrameStartTimePoint = now;
        this.lastFPSCalcTimePoint = now;
        super.start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && this.isMainActivityRunning()) {
            try {
                long frameLogicStartTimePoint = Time.now();

                this.lastFrameTimeDelta = frameLogicStartTimePoint - this.lastFrameStartTimePoint;
                this.lastFrameStartTimePoint = frameLogicStartTimePoint;

                if (this.drawing()) {
                    this.executeUpdate();
                    this.executeDraw();
                }

                long frameLogicStopTimePoint = Time.now();

                if (!this.gameFacade.configuration().unboundFrameRate) {
                    long frameLogicTimeDelta = Math.max(0, frameLogicStopTimePoint - frameLogicStartTimePoint);
                    long sleepTime = Math.max(0, (this.ticksPerFrame() - frameLogicTimeDelta) / (Time.resolution() / 1000));
                    Thread.sleep(sleepTime);
                }
            }
            catch (InterruptedException e) {
                break;
            }
            catch (Exception e) {
            }
        }
    }

    private boolean isMainActivityRunning() {
        Activity activity = this.activityWeakReference.get();
        return activity != null && !activity.isFinishing();
    }

    private long ticksPerFrame() {
        double resolution = (double)Time.resolution();
        return Math.round(resolution / this.gameFacade.configuration().framesPerSecond);
    }

    private void executeUpdate() {
        this.view.update();
        this.updateFPS();
    }

    private void updateFPS() {
        if (this.gameFacade.configuration().drawFramesPerSecond) {
            long calcInterval = (long) (this.gameFacade.configuration().framesPerSecondCalcInterval * Time.resolution());

            if (this.lastFrameStartTimePoint - this.lastFPSCalcTimePoint > calcInterval) {
                this.lastFPSCalcTimePoint = this.lastFrameStartTimePoint;
                if (this.lastFrameTimeDelta != 0) {
                    double value = Time.resolution() / (double) this.lastFrameTimeDelta;
                    this.fps = Math.round(value * 1000) / 1000f;
                } else {
                    this.fps = 0;
                }
            }
        }
    }

    private void executeDraw() {
        SurfaceHolder surfaceHolder = this.view.getHolder();
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                this.view.onDraw(canvas);
                this.drawFPS(canvas);
            }
        }
        finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawFPS(@NonNull Canvas canvas) {
        Configuration configuration = this.gameFacade.configuration();

        if (configuration.drawFramesPerSecond) {
            Paint paint = new Paint();
            paint.setTextSize(75);
            if (this.fps > configuration.framesPerSecond * 0.9)
                paint.setColor(Color.GREEN);
            else if (this.fps > configuration.framesPerSecond * 0.5)
                paint.setColor(Color.YELLOW);
            else
                paint.setColor(Color.RED);

            canvas.drawText(String.valueOf(this.fps), 10, 85, paint);
        }
    }

}
