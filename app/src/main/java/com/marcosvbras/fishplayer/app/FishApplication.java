package com.marcosvbras.fishplayer.app;

import android.app.Application;

import com.marcosvbras.fishplayer.app.domain.SimpleMusic;

import java.util.List;

/**
 * Created by marcosvbras on 10/02/17.
 */

public class FishApplication extends Application {

    public static List<SimpleMusic> oldMusicList;
    public static List<SimpleMusic> currentMusicList;
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
