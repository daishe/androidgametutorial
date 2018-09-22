package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;

public final class ImageManager implements GameFacadeComponent {

    private GameFacade gameFacade;

    private Image asteroidTiny0 = new Image();
    private Image asteroidTiny1 = new Image();
    private Image asteroidTiny2 = new Image();
    private Image asteroidTiny3 = new Image();
    private Image asteroidSmall0 = new Image();
    private Image asteroidSmall1 = new Image();
    private Image asteroidSmall2 = new Image();
    private Image asteroidMedium0 = new Image();
    private Image asteroidMedium1 = new Image();
    private Image asteroidLarge0 = new Image();
    private Image asteroidLarge1 = new Image();

    public ImageManager(@NonNull GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    public Image randomAsteroid() {
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

        this.asteroidTiny0.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_0));
        this.asteroidTiny1.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_1));
        this.asteroidTiny2.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_2));
        this.asteroidTiny3.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_tiny_3));
        this.asteroidSmall0.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_small_0));
        this.asteroidSmall1.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_small_1));
        this.asteroidSmall2.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_small_2));
        this.asteroidMedium0.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_medium_0));
        this.asteroidMedium1.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_medium_1));
        this.asteroidLarge0.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_large_0));
        this.asteroidLarge1.reset(ImageLoader.with().context(context).scale(scale).fromSvg(R.drawable.ic_asteroid_large_1));
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
        this.asteroidTiny0.reset();
        this.asteroidTiny1.reset();
        this.asteroidTiny2.reset();
        this.asteroidTiny3.reset();
        this.asteroidSmall0.reset();
        this.asteroidSmall1.reset();
        this.asteroidSmall2.reset();
        this.asteroidMedium0.reset();
        this.asteroidMedium1.reset();
        this.asteroidLarge0.reset();
        this.asteroidLarge1.reset();
    }

}
