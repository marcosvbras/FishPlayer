package com.marcosvbras.fishplayer.app;

import android.app.Application;

import com.marcosvbras.fishplayer.app.domain.Music;

import java.util.List;

/**
 * Created by marcosvbras on 10/02/17.
 */

public class FishApplication extends Application {

    private static FishApplication instance;

    public static FishApplication getInstance() { return instance; }

    public static List<Music> oldListMusic;
    public static List<Music> listMusic;

    public static int currentMusicIndex;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        currentMusicIndex = -1;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
