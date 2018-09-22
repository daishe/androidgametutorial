package com.github.daishe.androidgametutorial;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.SparseArray;

public final class SoundManager implements GameFacadeComponent {

    private GameFacade gameFacade;

    private SoundPool soundPool;

    private SparseArray<SoundSample> sampleIdToSoundSample = new SparseArray<SoundSample>();
    private SparseArray<SoundStream> streamIdToSoundStream = new SparseArray<SoundStream>();

    private SoundSample smallCollisionSound;
    private SoundSample largeCollisionSound;

    public SoundManager(GameFacade gameFacade) {
        this.gameFacade = gameFacade;

        this.smallCollisionSound = new SoundSample(this.gameFacade);
        this.largeCollisionSound = new SoundSample(this.gameFacade);
    }

    public SoundSample smallCollisionSound() {
        return this.smallCollisionSound;
    }

    public SoundSample largeCollisionSound() {
        return this.largeCollisionSound;
    }

    public boolean ready() {
        for (int i = 0; i < this.sampleIdToSoundSample.size(); ++i)
            if (!this.sampleIdToSoundSample.valueAt(i).ready())
                return false;
        return true;
    }

    public SoundStream play(@NonNull SoundSample soundSample, float leftVolume, float rightVolume) {
        int streamId = this.soundPool.play(soundSample.id(), leftVolume, rightVolume, 1, 0, 1);
        if (streamId == 0)
            return null;

        SoundStream soundStream = new SoundStream(this.gameFacade, soundSample, streamId);
        this.streamIdToSoundStream.put(streamId, soundStream);
        return soundStream;
    }

    public void setVolume(@NonNull SoundStream soundStream, float leftVolume, float rightVolume) {
        if (this.streamIdToSoundStream.get(soundStream.id()) == soundStream)
            this.soundPool.setVolume(soundStream.id(), leftVolume, rightVolume);
    }

    @Override
    public void create(@NonNull Context context) {
        this.soundPool = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? makeNewSoundPool() : makeOldSoundPool();

        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (status == 0)
                this.sampleIdToSoundSample.get(sampleId).ready(true);
        });

        this.loadResource(context, this.smallCollisionSound, R.raw.small_collision_sound);
        this.loadResource(context, this.largeCollisionSound, R.raw.large_collision_sound);
    }

    @Override
    public void pause() {
        this.soundPool.autoPause();
    }

    @Override
    public void resume() {
        this.soundPool.autoResume();
    }

    @Override
    public void destroy() {
        for (int i = 0; i < this.sampleIdToSoundSample.size(); ++i) {
            SoundSample soundSample = this.sampleIdToSoundSample.valueAt(i);
            soundSample.ready(false);
            this.soundPool.unload(soundSample.id());
            soundSample.clear();
        }
        this.soundPool.release();
        this.soundPool = null;

        this.sampleIdToSoundSample.clear();
        this.streamIdToSoundStream.clear();
    }

    private void loadResource(@NonNull Context context, @NonNull SoundSample soundSample, int resourceId) {
        soundSample.id(this.soundPool.load(context, resourceId, 1));
        this.sampleIdToSoundSample.append(soundSample.id(), soundSample);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    private SoundPool makeNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setMaxStreams(this.gameFacade.configuration().maxSimultaneousSoundStreams)
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private SoundPool makeOldSoundPool() {
        return new SoundPool(
                this.gameFacade.configuration().maxSimultaneousSoundStreams,
                AudioManager.STREAM_MUSIC,
                0
        );
    }

}
