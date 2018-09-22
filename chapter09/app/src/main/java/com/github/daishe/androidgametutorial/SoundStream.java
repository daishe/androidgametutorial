package com.github.daishe.androidgametutorial;

import android.support.annotation.NonNull;

public class SoundStream {

    private GameFacade gameFacade;

    private SoundSample soundSample;
    private final int id;

    public SoundStream(@NonNull GameFacade gameFacade, @NonNull SoundSample soundSample, int id) {
        this.gameFacade = gameFacade;
        this.soundSample = soundSample;
        this.id = id;
    }

    @NonNull
    public SoundSample sample() {
        return this.soundSample;
    }

    public int id() {
        return this.id;
    }

    public void setVolume(@NonNull SoundStream soundStream, float volume) {
        this.gameFacade.soundManager().setVolume(soundStream, volume, volume);
    }

    public void setVolume(@NonNull SoundStream soundStream, float leftVolume, float rightVolume) {
        this.gameFacade.soundManager().setVolume(soundStream, leftVolume, rightVolume);
    }

}
