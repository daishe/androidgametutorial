package com.github.daishe.androidgametutorial;

public class Collision {

    public static Vector2D at(Vector2D objectAPosition, float objectARadius, Vector2D objectBPosition, float objectBRadius) {
        float xDelta = objectAPosition.x() - objectBPosition.x();
        float yDelta = objectAPosition.y() - objectBPosition.y();
        float radiusSum = objectARadius + objectBRadius;

        boolean isXNear = xDelta > -radiusSum && xDelta < radiusSum;
        boolean isYNear = yDelta > -radiusSum && yDelta < radiusSum;
        boolean isNear = isXNear && isYNear;

        if (!isNear)
            return null;

        MutableVector2D positionDifference = Vector2D.from(objectAPosition).subInPlace(objectBPosition);
        boolean inCollision = positionDifference.radiusSquare() < radiusSum * radiusSum;

        if (!inCollision)
            return null;

        return positionDifference.mulInPlace(objectBRadius / radiusSum).addInPlace(objectBPosition);
    }

}
