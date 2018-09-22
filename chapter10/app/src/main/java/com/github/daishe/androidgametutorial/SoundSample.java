package com.github.daishe.androidgametutorial;

import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class SoundSample {

    private GameFacade gameFacade;

    private int id;
    private AtomicBoolean ready = new AtomicBoolean(false);

    public SoundSample(@NonNull GameFacade gameFacade) {
        this.gameFacade = gameFacade;
        this.clear();
    }

    public int id() {
        return this.id;
    }

    public int id(int id) {
        return this.id = id;
    }

    public boolean ready() {
        return this.ready.get();
    }

    public boolean ready(boolean setTo) {
        this.ready.set(setTo);
        return setTo;
    }

    public void clear() {
        this.id(0);
        this.ready(false);
    }

    public SoundStream play() {
        return this.gameFacade.soundManager().play(this, 1, 1);
    }

    public SoundStream play(float volume) {
        return this.gameFacade.soundManager().play(this, volume, volume);
    }

    public SoundStream play(float leftVolume, float rightVolume) {
        return this.gameFacade.soundManager().play(this, leftVolume, rightVolume);
    }

    public SoundStream play(Vector2D relativePosition) {
        Configuration configuration = this.gameFacade.configuration();

        float distance = relativePosition.radius();

        float volume = 0;
        if (distance < configuration.clearHearingRange)
            volume = 1;
        else if (distance < configuration.clearHearingRange + configuration.decayingHearingSpaceRange)
            volume = (configuration.clearHearingRange + configuration.decayingHearingSpaceRange - distance) / configuration.decayingHearingSpaceRange;
        else
            volume = 0;

        if (Float.isNaN(volume) || volume <= 0)
            return null;
        return this.gameFacade.soundManager().play(this, volume, volume);
    }

}
