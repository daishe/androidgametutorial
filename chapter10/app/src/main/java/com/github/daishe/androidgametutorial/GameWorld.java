package com.github.daishe.androidgametutorial;

import java.util.ArrayList;

public class GameWorld {

    private GameFacade gameFacade;

    private PlayerShip playerShip;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Rocket> rockets;

    private float rocketFireTimeout = 0;

    public GameWorld(GameFacade gameFacade) {
        this.gameFacade = gameFacade;

        this.playerShip = new PlayerShip().create(this);

        this.asteroids = new ArrayList<Asteroid>();
        while (this.asteroids.size() < this.gameFacade.configuration().asteroidsLimit)
            this.asteroids.add(createAsteroid(new Asteroid()));

        this.rockets = new ArrayList<Rocket>();
    }

    public GameFacade facade() {
        return this.gameFacade;
    }

    public Vector2D cameraPosition() {
        return this.playerShip.position();
    }

    public PlayerShip player() {
        return this.playerShip;
    }

    public Vector2D orientationVector() {
        return this.gameFacade.orientation().snapshot().asInGameVector2D();
    }

    public void update(float timeShift) {

        if (this.rocketFireTimeout <= 0) {
            TouchEvent touchEvent = this.facade().touch().get();
            if (touchEvent != null && this.playerShip.alive()) {
                this.rockets.add(new Rocket().create(this, touchEvent.gamePosition(this.cameraPosition())));
                this.rocketFireTimeout = this.gameFacade.configuration().rocketFireTimeout;
            }
        }
        else {
            this.rocketFireTimeout -= timeShift;
        }

        this.playerShip.update(timeShift);
        for (Asteroid asteroid : this.asteroids)
            asteroid.update(timeShift);
        for (Rocket rocket : this.rockets)
            rocket.update(timeShift);

        this.resolveCollisions();

        for (Asteroid asteroid : this.asteroids)
            if (!asteroid.alive())
                this.createAsteroid(asteroid);
        for (int i = 0; i < this.rockets.size(); ++i)
            if (!this.rockets.get(i).alive())
                this.rockets.remove(i);
    }

    public void draw(GameCanvas gameCanvas) {
        this.playerShip.draw(gameCanvas);
        for (Asteroid asteroid : this.asteroids)
            asteroid.draw(gameCanvas);
        for (Rocket rocket : this.rockets)
            rocket.draw(gameCanvas);

        if (this.gameFacade.configuration().drawPhysicsDebugInfo) {
            TouchEvent touchEvent = this.gameFacade.touch().get();
            if (touchEvent != null)
                gameCanvas.drawDebugTouch(touchEvent.gamePosition(this.cameraPosition()));
        }
    }

    private Asteroid createAsteroid(Asteroid asteroid) {
        Configuration configuration = this.gameFacade.configuration();
        Random random = this.gameFacade.random();

        float radius = random.linear() * configuration.spawnSpaceRange + configuration.minSpawnRange;
        float theta = random.angle();
        asteroid.create(this, Vector2D.fromPolar(radius, theta).addInPlace(this.cameraPosition()));
        return asteroid;
    }

    private void resolveCollisions() {
        this.resolveCollisionsBetweenPlayerShipAndAsteroid();
        this.resolveCollisionsBetweenAsteroidAndAsteroid();
        this.resolveCollisionsBetweenAsteroidAndRocket();
    }

    private void resolveCollisionsBetweenPlayerShipAndAsteroid() {
        if (!this.playerShip.alive())
            return;

        for (int i = 0; i < this.asteroids.size(); ++i) {
            Asteroid asteroid = this.asteroids.get(i);

            if (!asteroid.alive())
                continue;

            Vector2D collisionPoint = Collision.at(
                    this.playerShip.position(),
                    this.playerShip.collisionRadius(),
                    asteroid.position(),
                    asteroid.collisionRadius()
                );

            if (collisionPoint != null) {
                this.playerShip.alive(false);
                asteroid.alive(false);
                this.facade().soundManager().largeCollisionSound().play(collisionPoint.sub(this.cameraPosition()));
            }
        }
    }

    private void resolveCollisionsBetweenAsteroidAndAsteroid() {
        for (int i = 0; i < this.asteroids.size(); ++i) {
            Asteroid first = this.asteroids.get(i);

            if (!first.alive())
                continue;

            for (int j = i + 1; j < this.asteroids.size(); ++j) {
                Asteroid second = this.asteroids.get(j);

                if (!second.alive())
                    continue;

                Vector2D collisionPoint = Collision.at(
                        first.position(),
                        first.collisionRadius(),
                        second.position(),
                        second.collisionRadius()
                    );

                if (collisionPoint != null) {
                    first.alive(false);
                    second.alive(false);
                    this.facade().soundManager().smallCollisionSound().play(collisionPoint.sub(this.cameraPosition()));
                }
            }
        }
    }

    private void resolveCollisionsBetweenAsteroidAndRocket() {
        for (int i = 0; i < this.asteroids.size(); ++i) {
            Asteroid asteroid = this.asteroids.get(i);

            if (!asteroid.alive())
                continue;

            for (int j = 0; j < this.rockets.size(); ++j) {
                Rocket rocket = this.rockets.get(j);

                if (!rocket.alive())
                    continue;

                Vector2D collisionPoint = Collision.at(
                        asteroid.position(),
                        asteroid.collisionRadius(),
                        rocket.position(),
                        rocket.collisionRadius()
                );

                if (collisionPoint != null) {
                    asteroid.alive(false);
                    rocket.alive(false);
                    this.facade().soundManager().smallCollisionSound().play(collisionPoint.sub(this.cameraPosition()));
                }
            }
        }
    }

}
