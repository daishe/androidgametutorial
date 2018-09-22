package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView {

    private GameFacade gameFacade;

    private ArrayList<Asteroid> asteroids = null;

    private float lastUpdateTime;

    public GameView(Context context, @NonNull GameFacade gameFacade) {
        super(context);

        this.gameFacade = gameFacade;

        this.lastUpdateTime = this.gameFacade.timer().timeAsFloat();
    }

    protected void update() {
        if (this.asteroids == null)
            this.createAsteroids();

        float updateTime = this.gameFacade.timer().timeAsFloat();
        float timeShift = updateTime - this.lastUpdateTime;
        timeShift = timeShift > 0 ? timeShift : 0;
        this.lastUpdateTime = updateTime;

        for (Asteroid asteroid : this.asteroids)
            asteroid.update(timeShift);
    }

    private void createAsteroids() {
        Random r = this.gameFacade.random();

        this.asteroids = new ArrayList<Asteroid>();

        Vector2D center = Vector2D.fromCartesian(this.getWidth() / 2, this.getHeight() / 2);
        float averageOrbitingRadius = this.getHeight() / 6;
        for (int i = 0; i < 10; ++i) {
            Bitmap asteroidBitmap = this.gameFacade.imageManager().randomAsteroid();

            float orbitingRadius = averageOrbitingRadius * (1f + 0.3f * r.gaussian());
            Vector2D relativePosition = Vector2D.fromPolar(orbitingRadius, r.angle());
            Vector2D velocity = Vector2D.fromCartesian(-relativePosition.y(), relativePosition.x())
                .mulInPlace(1f + 0.2f * r.gaussian())
                .mulInPlace(r.sign());

            this.asteroids.add(new Asteroid(asteroidBitmap, center, center.add(relativePosition), velocity));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFF2E2957);
        for (Asteroid asteroid : this.asteroids)
            asteroid.draw(canvas);
    }

}
