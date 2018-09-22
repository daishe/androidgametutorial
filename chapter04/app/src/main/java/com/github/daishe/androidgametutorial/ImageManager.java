package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public final class ImageManager implements GameFacadeComponent {

    private GameFacade gameFacade;

    private Bitmap asteroidTiny0 = null;
    private Bitmap asteroidTiny1 = null;
    private Bitmap asteroidTiny2 = null;
    private Bitmap asteroidTiny3 = null;
    private Bitmap asteroidSmall0 = null;
    private Bitmap asteroidSmall1 = null;
    private Bitmap asteroidSmall2 = null;
    private Bitmap asteroidMedium0 = null;
    private Bitmap asteroidMedium1 = null;
    private Bitmap asteroidLarge0 = null;
    private Bitmap asteroidLarge1 = null;

    public ImageManager(@NonNull GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    public Bitmap randomAsteroid() {
        Random r = this.gameFacade.random();
        double randomGaussian = Math.abs(r.gaussian(2));
        if (randomGaussian < 0.5) {
            switch(r.integer(4)) {
                case 0:  return this.asteroidTiny0;
                case 1:  return this.asteroidTiny1;
                case 2:  return this.asteroidTiny2;
                default: return this.asteroidTiny3;
            }
        }
        if (randomGaussian < 1.0) {
            switch(r.integer(4)) {
                case 0:  return this.asteroidSmall0;
                case 1:  return this.asteroidSmall1;
                default: return this.asteroidSmall2;
            }

        }
        if (randomGaussian < 1.5) {
            switch(r.integer(2)) {
                case 0:  return this.asteroidMedium0;
                default: return this.asteroidMedium1;
            }
        }
        switch(r.integer(2)) {
            case 0:  return this.asteroidLarge0;
            default: return this.asteroidLarge1;
        }
    }

    @Override
    public void create(@NonNull Context context) {
        float scale = 0.04f;

        this.asteroidTiny0 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_0);
        this.asteroidTiny1 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_1);
        this.asteroidTiny2 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_2);
        this.asteroidTiny3 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_3);
        this.asteroidSmall0 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_small_0);
        this.asteroidSmall1 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_small_1);
        this.asteroidSmall2 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_small_2);
        this.asteroidMedium0 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_medium_0);
        this.asteroidMedium1 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_medium_1);
        this.asteroidLarge0 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_large_0);
        this.asteroidLarge1 = ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_large_1);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
        this.asteroidTiny0 = null;
        this.asteroidTiny1 = null;
        this.asteroidTiny2 = null;
        this.asteroidTiny3 = null;
        this.asteroidSmall0 = null;
        this.asteroidSmall1 = null;
        this.asteroidSmall2 = null;
        this.asteroidMedium0 = null;
        this.asteroidMedium1 = null;
        this.asteroidLarge0 = null;
        this.asteroidLarge1 = null;
    }

}
