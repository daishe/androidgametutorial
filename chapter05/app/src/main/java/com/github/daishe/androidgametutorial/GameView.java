package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    private GameFacade gameFacade;
    private GameWorld world;
    private GameCanvas gameCanvas;

    private float lastUpdateTime;

    public GameView(Context context, @NonNull GameFacade gameFacade, @NonNull GameWorld world) {
        super(context);

        this.gameFacade = gameFacade;
        this.world = world;
        this.gameCanvas = new GameCanvas();

        this.lastUpdateTime = this.gameFacade.timer().timeAsFloat();
    }

    protected void update() {
        float updateTime = this.gameFacade.timer().timeAsFloat();
        float timeShift = updateTime - this.lastUpdateTime;
        timeShift = timeShift > 0 ? timeShift : 0;
        this.lastUpdateTime = updateTime;

        this.world.update(timeShift);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.gameCanvas.lockCanvas(canvas);
        this.world.draw(this.gameCanvas);
        this.gameCanvas.releaseCanvas();
    }

}
