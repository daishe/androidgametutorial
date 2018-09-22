package com.github.daishe.androidgametutorial;

public class PlayerShip {

    private GameWorld world;

    private MutableVector2D position = Vector2D.fromNothing();
    private MutableVector2D velocity = Vector2D.fromNothing();

    private Image shipImage;

    private boolean alive = false;

    public PlayerShip() {
    }

    public PlayerShip create(GameWorld world) {
        this.world = world;

        this.velocity.setToCartesian(0, 0);
        this.position.setToCartesian(0, 0);

        this.shipImage = world.facade().imageManager().ship();

        this.alive = true;

        return this;
    }

    public GameWorld world() {
        return this.world;
    }

    public GameWorld world(GameWorld world) {
        return this.world = world;
    }

    public boolean alive() {
        return this.alive;
    }

    public boolean alive(boolean alive) {
        return this.alive = alive;
    }

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
        return this.shipImage.radius();
    }

    public void update(float timeShift) {
        Configuration configuration = this.world.facade().configuration();

        Vector2D velocityIncrement = this.world.orientationVector()
            .mul(timeShift * configuration.playerShipMaxAcceleration);
        this.velocity
            .addInPlace(velocityIncrement)
            .limitInPlace(configuration.playerShipMaxVelocity);

        this.position.addInPlace(this.velocity.mul(timeShift));
    }

    public void draw(GameCanvas gameCanvas) {
        if (!this.alive)
            return;

        Configuration configuration = this.world.facade().configuration();

        if (configuration.drawPhysicsDebugInfo) {
            gameCanvas.drawDebugVelocity(this.position, this.velocity);
            gameCanvas.drawDebugAcceleration(this.position, this.world.orientationVector().mul(this.world.facade().configuration().playerShipMaxAcceleration));
            gameCanvas.drawDebugCollisionBox(this.position, this.shipImage.radius());
        }

        for (int i = configuration.numberOfPlayerShipTrails - 1; i > -1; --i) {
            Vector2D pos = Vector2D.from(this.position)
                    .subInPlace(this.velocity.mul(configuration.playerShipTrailOffset / configuration.playerShipMaxVelocity * i));

            float scale = 1f + configuration.playerShipTrailScaleFactor * i;
            float alpha = (i != 0) ? (configuration.playerShipTrailAlphaFactor / i) : 1f;
            this.shipImage.draw(gameCanvas, pos, scale, 0f, alpha);
        }
    }

}
