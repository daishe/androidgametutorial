package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GameView extends View {

    private Paint paint = new Paint();

    public GameView(Context context) {
        super(context);
        this.paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 4, 30, paint);
    }

}