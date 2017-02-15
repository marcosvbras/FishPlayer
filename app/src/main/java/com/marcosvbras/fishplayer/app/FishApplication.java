package com.marcosvbras.fishplayer.app;

import android.app.Application;

import com.marcosvbras.fishplayer.app.domain.Music;

import java.util.List;

/**
 * Created by marcosvbras on 10/02/17.
 */

public class FishApplication extends Application {

    public static List<Music> oldListMusic;
    public static List<Music> currentListMusic;

    public static int currentMusicIndex;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
