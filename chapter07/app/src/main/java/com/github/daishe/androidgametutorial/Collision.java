package com.github.daishe.androidgametutorial;

public class Collision {

    public static boolean at(Vector2D objectAPosition, float objectARadius, Vector2D objectBPosition, float objectBRadius) {
        float xDelta = objectAPosition.x() - objectBPosition.x();
        float yDelta = objectAPosition.y() - objectBPosition.y();
        float radiusSum = objectARadius + objectBRadius;

        boolean isXNear = xDelta > -radiusSum && xDelta < radiusSum;
        boolean isYNear = yDelta > -radiusSum && yDelta < radiusSum;
        boolean isNear = isXNear && isYNear;

        if (!isNear)
            return false;

        MutableVector2D positionDifference = Vector2D.from(objectAPosition).subInPlace(objectBPosition);
        return positionDifference.radiusSquare() < radiusSum * radiusSum;
    }

}
