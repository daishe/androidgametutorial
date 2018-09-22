package com.github.daishe.androidgametutorial;

public class Rocket {

    private static final float radius = 5f;

    private GameWorld world;

    private MutableVector2D position = Vector2D.fromNothing();
    private MutableVector2D velocity = Vector2D.fromNothing();
    private MutableVector2D acceleration = Vector2D.fromNothing();

    private MutableVector2D initialPosition = Vector2D.fromNothing();
    private MutableVector2D targetPosition = Vector2D.fromNothing();
    private float initialTargetDistanceSquare = 0;

    private boolean homing = false;

    private DynamicAlpha alpha = new DynamicAlpha(0.9f, 0.25f, 0.3f);

    private boolean alive = false;

    public Rocket() {
    }

    public Rocket create(GameWorld world, Vector2D targetPosition) {
        GameFacade gameFacade = world.facade();

        this.world = world;

        this.position.setTo(this.world.player().position());
        this.velocity.setTo(this.world.player().velocity())
            .normalizeInPlace()
            .mulInPlace(gameFacade.configuration().rocketMaxVelocity);
        this.acceleration.setToCartesian(0, 0);

        this.initialPosition.setTo(this.position);
        this.targetPosition.setTo(targetPosition);
        this.initialTargetDistanceSquare = Vector2D.from(this.targetPosition)
            .subInPlace(this.initialPosition)
            .radiusSquare();

        this.homing = true;

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
        return Rocket.radius;
    }

    public void update(float timeShift) {
        if (!this.alive)
            return;

        Configuration configuration = this.world.facade().configuration();

        Vector2D relativePosition = Vector2D.from(this.position).subInPlace(this.world.cameraPosition());
        if (Math.abs(relativePosition.x()) > configuration.killingRange || Math.abs(relativePosition.y()) > configuration.killingRange)
            this.alive = false;

        this.alpha.update(timeShift);

        if (this.homing) {
            Vector2D homingVector = Vector2D.from(this.targetPosition)
                    .subInPlace(this.position);
            this.acceleration.setTo(homingVector)
                .normalizeInPlace()
                .mulInPlace(configuration.rocketMaxAcceleration);

            if (Vector2D.from(this.position).subInPlace(this.initialPosition).radiusSquare() > this.initialTargetDistanceSquare)
                this.homing = false;
        }
        else {
            this.acceleration.setTo(this.velocity)
                .normalizeInPlace()
                .mulInPlace(configuration.rocketMaxAcceleration);
        }

        Vector2D velocityIncrement = Vector2D.from(this.acceleration).mul(timeShift);
        this.velocity
                .addInPlace(velocityIncrement)
                .limitInPlace(configuration.rocketMaxVelocity);

        this.position.addInPlace(this.velocity.mul(timeShift));
    }

    public void draw(GameCanvas gameCanvas) {
        if (!this.alive || !gameCanvas.isVisible(this.position, Rocket.radius))
            return;

        if (this.world.facade().configuration().drawPhysicsDebugInfo) {
            gameCanvas.drawDebugHomingInfo(this.initialPosition, this.position, this.targetPosition);
            gameCanvas.drawDebugVelocity(this.position, this.velocity);
            gameCanvas.drawDebugAcceleration(this.position, this.acceleration);
            gameCanvas.drawDebugCollisionBox(this.position, Rocket.radius);
        }

        gameCanvas.drawCircle(Rocket.radius, this.position, this.alpha.get(), 0xFFFF0800);
    }

}
