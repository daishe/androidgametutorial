package com.github.daishe.androidgametutorial;

import java.util.ArrayList;

public class GameWorld {

    private GameFacade gameFacade;

    private ArrayList<Asteroid> asteroids;

    public GameWorld(GameFacade gameFacade) {
        this.gameFacade = gameFacade;

        this.asteroids = new ArrayList<Asteroid>();
        for (int i = 0; i < 10; ++i)
            this.asteroids.add(this.createAsteroid());
    }

    public void update(float timeShift) {
        for (Asteroid asteroid : this.asteroids)
            asteroid.update(timeShift);
    }

    public void draw(GameCanvas gameCanvas) {
        for (Asteroid asteroid : this.asteroids)
            asteroid.draw(gameCanvas);
    }

    private Asteroid createAsteroid() {
        Random r = this.gameFacade.random();

        Image asteroidImage = this.gameFacade.imageManager().randomAsteroid();

        float orbitingRadius = 320f * (1f + 0.3f * r.gaussian());
        Vector2D relativePosition = Vector2D.fromPolar(orbitingRadius, r.angle());
        Vector2D velocity = Vector2D.fromCartesian(-relativePosition.y(), relativePosition.x())
                .mulInPlace(1f + 0.2f * r.gaussian())
                .mulInPlace(r.sign());

        return new Asteroid(
                asteroidImage,
                Vector2D.fromCartesian(0, 0),
                relativePosition,
                velocity,
                r.angle(),
                r.gaussian(8) * (float)Math.PI / 2
            );
    }

}
