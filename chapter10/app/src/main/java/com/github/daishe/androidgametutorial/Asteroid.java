package com.github.daishe.androidgametutorial;

public class Asteroid {

    private GameWorld world;

    private MutableVector2D position = Vector2D.fromNothing();
    private MutableVector2D velocity = Vector2D.fromNothing();
    private float angularVelocity;
    private float angularPosition;

    private Image asteroidImage;

    private boolean alive = false;

    public Asteroid() {
    }

    public Asteroid create(GameWorld world, Vector2D inWorldPosition) {
        GameFacade gameFacade = world.facade();
        Configuration configuration = gameFacade.configuration();

        this.world = world;

        this.velocity.setToPolar(
                configuration.asteroidMaxVelocity - Math.abs(gameFacade.random().gaussian(3) * configuration.asteroidMaxVelocity / 3),
                world.cameraPosition().sub(inWorldPosition).theta() + gameFacade.random().gaussian() * (float)Math.PI / 3
            );
        this.position.setTo(inWorldPosition);
        this.angularVelocity = gameFacade.random().gaussian(8) * (float)Math.PI / 2;
        this.angularPosition = gameFacade.random().angle();

        this.asteroidImage = gameFacade.imageManager().randomAsteroid();

        this.alive = true;

        return this;
    }

    public GameWorld world() {
        return this.world;
    }

    public GameWorld world(GameWorld world) {
        return this.world = world;
    }

    public boolean alive() { return this.alive; }

    public boolean alive(boolean alive) { return this.alive = alive; }

    public Vector2D position() {
        return this.position;
    }

    public Vector2D position(Vector2D position) {
        return this.position.setTo(position);
    }

    public Vector2D velocity() {
        return this.velocity;
    }

    public Vector2D velocity(Vector2D velocity) {
        return this.velocity.setTo(velocity);
    }

    public float collisionRadius() {
        return this.asteroidImage.radius();
    }

    public void update(float timeShift) {
        if (!this.alive)
            return;

        Configuration configuration = this.world.facade().configuration();

        Vector2D relativePosition = Vector2D.from(this.position).subInPlace(this.world.cameraPosition());
        if (Math.abs(relativePosition.x()) > configuration.killingRange || Math.abs(relativePosition.y()) > configuration.killingRange)
            this.alive = false;

        this.position.addInPlace(this.velocity.mul(timeShift));

        this.angularPosition += this.angularVelocity * timeShift;
        this.angularPosition -= ((int)(this.angularPosition / (float)(Math.PI * 2))) * (float)(Math.PI * 2);
    }

    public void draw(GameCanvas gameCanvas) {
        if (!this.alive || !gameCanvas.isVisible(this.position, this.asteroidImage.diagonal() / 2))
            return;

        if (this.world.facade().configuration().drawPhysicsDebugInfo) {
            gameCanvas.drawDebugVelocity(this.position, this.velocity);
            gameCanvas.drawDebugCollisionBox(this.position, this.asteroidImage.radius());
        }

        this.asteroidImage.draw(gameCanvas, this.position, 1f, this.angularPosition, 1f);
    }

}
