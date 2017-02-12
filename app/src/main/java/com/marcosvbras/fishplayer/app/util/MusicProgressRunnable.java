package com.marcosvbras.fishplayer.app.util;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.marcosvbras.fishplayer.app.interfaces.OnMusicProgressChangeListener;

/**
 * Created by marcosvbras on 11/02/17.
 */

public class MusicProgressRunnable implements Runnable {

    private int status;
    private MediaPlayer mediaPlayer;
    private OnMusicProgressChangeListener onMusicProgressChangeListener;
    private Handler handler;

    public MusicProgressRunnable(MediaPlayer mediaPlayer, OnMusicProgressChangeListener onMusicProgressChangeListener) {
        status = Constants.STATUS_STOPPED;
        this.mediaPlayer = mediaPlayer;
        this.onMusicProgressChangeListener = onMusicProgressChangeListener;
        handler = new Handler();
    }

    @Override
    public void run() {
        if(status != Constants.STATUS_PAUSED) {
            if(onMusicProgressChangeListener != null && mediaPlayer != null) {
                onMusicProgressChangeListener.onProgressChange(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
            }
        }
    }

    /**
     * Call this to pause.
     */
    public void pause() {
        status = Constants.STATUS_PAUSED;
    }

    /**
     * Call this to resume.
     */
    public void resume() {
        status = Constants.STATUS_RUNNING;
        run();
    }

    public boolean isPaused() {
        return status == Constants.STATUS_PAUSED;
    }
}
