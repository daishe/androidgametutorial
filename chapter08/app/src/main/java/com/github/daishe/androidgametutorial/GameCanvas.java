package com.github.daishe.androidgametutorial;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GameCanvas {

    private GameFacade gameFacade;

    private Canvas canvas = null;

    private Paint paint = new Paint();
    private Matrix matrix = new Matrix();

    private float lastScale = 1f;

    public GameCanvas(GameFacade gameFacade) {
        this.gameFacade = gameFacade;

        this.paint.setAntiAlias(true);
    }

    public boolean isVisible(Vector2D point, float toleration) {
        float width = this.canvas.getWidth() / this.lastScale / 2;
        float height = this.canvas.getHeight() / this.lastScale / 2;
        float x = point.x();
        float y = point.y();

        boolean xVisible = !(x + toleration < -width || x - toleration > width);
        boolean yVisible = !(y + toleration < -height || y - toleration > height);
        return xVisible && yVisible;
    }

    public void drawVector2D(Vector2D vector, Vector2D at, int color) {
        this.paint.setColor(color);
        this.canvas.drawLine(
                at.x(),
                at.y(),
                at.x() + vector.x(),
                at.y() + vector.y(),
                this.paint
        );
        this.paint.setColor(0xFF000000);
    }

    public void drawCircle(float radius, Vector2D at, float alpha, int color) {
        this.paint.setColor(color);
        this.paint.setAlpha((int)(255 * alpha));
        this.canvas.drawCircle(at.x(), at.y(), radius, this.paint);
        this.paint.setAlpha(255);
        this.paint.setColor(0xFF000000);
    }

    public void drawBitmap(Bitmap bitmap, Vector2D at) {
        this.canvas.drawBitmap(
                bitmap,
                at.x() - bitmap.getWidth() / 2,
                at.y() - bitmap.getHeight() / 2,
                this.paint
        );
    }

    public void drawBitmap(Bitmap bitmap, Vector2D at, float scale, float rotation, float alpha) {
        this.matrix.setTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
        this.matrix.postScale(scale, scale);
        this.matrix.postRotate((float)Math.toDegrees(rotation), 0, 0);
        this.matrix.postTranslate(at.x(), at.y());

        this.paint.setAlpha((int)(255 * alpha));
        canvas.drawBitmap(bitmap, this.matrix, this.paint);
        this.paint.setAlpha(255);

        this.matrix.reset();
    }

    public void drawDebugTouch(Vector2D at) {
        this.drawCircle(20f, at, 0.5f, Color.YELLOW);
    }

    public void drawDebugAcceleration(Vector2D at, Vector2D acceleration) {
        this.drawVector2D(acceleration, at, Color.YELLOW);
    }

    public void drawDebugVelocity(Vector2D at, Vector2D velocity) {
        this.drawVector2D(velocity, at, Color.GREEN);
    }

    public void drawDebugCollisionBox(Vector2D at, float radius) {
        this.paint.setColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.STROKE);

        // Collision circle
        this.canvas.drawCircle(at.x(), at.y(), radius, this.paint);

        // Collision box
        this.canvas.drawLine(at.x() - radius, at.y() - radius, at.x() + radius, at.y() - radius, this.paint);
        this.canvas.drawLine(at.x() - radius, at.y() - radius, at.x() - radius, at.y() + radius, this.paint);
        this.canvas.drawLine(at.x() + radius, at.y() + radius, at.x() - radius, at.y() + radius, this.paint);
        this.canvas.drawLine(at.x() + radius, at.y() + radius, at.x() + radius, at.y() - radius, this.paint);

        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(0xFF000000);
    }

    public void lockCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.canvas.save();

        this.canvas.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        this.lastScale = this.gameFacade.screen().scale();
        this.canvas.scale(this.lastScale, this.lastScale);

        this.canvas.drawColor(0xFF2E2957);

        if (this.gameFacade.configuration().drawPhysicsDebugInfo) {
            // Dimensional vectors
            this.drawVector2D(Vector2D.fromCartesian(100, 0), Vector2D.fromCartesian(0, 0), Color.RED);
            this.drawVector2D(Vector2D.fromCartesian(0, 100), Vector2D.fromCartesian(0, 0), Color.RED);

            // Dimensional vectors sum
            this.drawVector2D(Vector2D.fromCartesian(100, 100), Vector2D.fromCartesian(0, 0), Color.RED);

            // Minimum visible range
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setColor(Color.RED);
            this.canvas.drawCircle(0, 0, this.gameFacade.configuration().minVisibleRange, this.paint);
            this.paint.setStyle(Paint.Style.FILL);
        }
    }

    public Canvas releaseCanvas() {
        this.canvas.restore();

        Canvas result = this.canvas;
        this.canvas = null;
        return result;
    }

}
