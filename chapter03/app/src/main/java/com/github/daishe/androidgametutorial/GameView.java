package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    private Asteroid asteroid = null;

    private long lastUpdateTime;

    public GameView(Context context) {
        super(context);
        this.lastUpdateTime = Time.now();
    }

    protected void update() {
        if (this.asteroid == null)
            this.createAsteroid();

        long updateTime = Time.now();
        long timeDelta = updateTime - this.lastUpdateTime;
        timeDelta = timeDelta > 0 ? timeDelta : 0;
        this.lastUpdateTime = updateTime;

        float timeShift = (float)(timeDelta / (double)Time.resolution());
        this.asteroid.update(timeShift);
    }

    private void createAsteroid() {
        this.asteroid = new Asteroid(
                ImageLoader.with().context(this.getContext()).scale(0.04f).fromSvg(R.drawable.ic_asteroid_medium_0),
                Vector2D.fromCartesian(this.getWidth() / 2, this.getHeight() / 2),
                Vector2D.fromCartesian(this.getWidth() / 2, this.getHeight() / 4),
                Vector2D.fromCartesian(-150, 0)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFF2E2957);
        this.asteroid.draw(canvas);
    }

}
