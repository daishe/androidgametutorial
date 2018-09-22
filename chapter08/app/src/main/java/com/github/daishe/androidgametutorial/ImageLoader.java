package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public final class ImageLoader {

    private Context context = null;
    private int resource = 0;
    private float alpha = 1.f;
    private float scale = 1.f;

    public ImageLoader() {
    }

    @NonNull
    public static ImageLoader with() {
        return new ImageLoader();
    }

    @NonNull
    public ImageLoader context(Context context) {
        this.context = context;
        return this;
    }

    @NonNull
    public ImageLoader resource(int resource) {
        this.resource = resource;
        return this;
    }

    @NonNull
    public ImageLoader alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    @NonNull
    public ImageLoader scale(float scale) {
        this.scale = scale;
        return this;
    }

    @NonNull
    public Image fromSvg() {
        Drawable drawable = ContextCompat.getDrawable(this.context, this.resource);
        Bitmap bitmap = Bitmap.createBitmap(
                Math.round(drawable.getIntrinsicWidth() * this.scale),
                Math.round(drawable.getIntrinsicHeight() * this.scale),
                Bitmap.Config.ARGB_8888
        );

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight()
        );
        drawable.setAlpha(Math.round(this.alpha * 255));
        drawable.draw(canvas);

        return new Image(bitmap);
    }

    @NonNull
    public Image fromSvg(int resource) {
        return this.resource(resource).fromSvg();
    }

}
