package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    private static final int frames = 9;

    private Paint paint = new Paint();

    private Bitmap bitmap;
    private Rect src;
    private Rect dst;

    public GameView(Context context) {
        super(context);
        this.paint.setColor(Color.RED);

        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        this.src = new Rect(0, 0, 0, this.bitmap.getHeight());
        this.dst = new Rect();
    }

    protected void update() {
        this.frame(this.selectFrame(), this.getWidth() / 2, this.getHeight() / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFF2E2957);
        canvas.drawBitmap(this.bitmap, src, dst, paint);
    }

    private void frame(int n, int x, int y) {
        int width = this.bitmap.getWidth() / 9;
        int height = this.bitmap.getHeight();
        this.src.left = width * n;
        this.src.right = width * (n + 1);

        width /= 2;
        height /= 2;
        this.dst.left = x - width;
        this.dst.top = y - height;
        this.dst.right = x + width;
        this.dst.bottom = y + height;
    }

    private int selectFrame() {
        return (int)(Time.now() / (Time.resolution() / 18)) % 9;
    }

}
