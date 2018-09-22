package com.github.daishe.androidgametutorial;

import android.content.Context;
import android.support.annotation.NonNull;

public interface GameFacadeComponent {

    void create(@NonNull Context context);

    void pause();

    void resume();

    void destroy();

}
