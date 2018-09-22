package com.github.daishe.androidgametutorial;

public final class Time {

    public static long resolution() {
        return 1000000000;
    }

    public static long now() {
        return System.nanoTime();
    }

}
