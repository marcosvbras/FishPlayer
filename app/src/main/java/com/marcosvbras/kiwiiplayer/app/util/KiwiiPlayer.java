package com.marcosvbras.kiwiiplayer.app.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.marcosvbras.kiwiiplayer.R;

import java.io.IOException;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class KiwiiPlayer implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private String musicPath;
    private Context context;

    private final int STATUS_NEW = 0;
    private final int STATUS_PLAYING = 1;
    private final int STATUS_PAUSED = 2;
    private final int STATUS_STOPPED = 3;

    private int status = STATUS_NEW;

    public KiwiiPlayer(Context context) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        this.context = context;
    }

    public void play(String musicPath) {
        this.musicPath = musicPath;

        try{
            switch (status) {
                case STATUS_PLAYING:
                    mediaPlayer.stop();
                    break;
                case STATUS_STOPPED:
                    mediaPlayer.reset();
                    break;
                case STATUS_NEW:
                    mediaPlayer.setDataSource(musicPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    break;
                case STATUS_PAUSED:
                    mediaPlayer.start();
                    break;
            }

            status = STATUS_PLAYING;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mediaPlayer.pause();
        status = STATUS_PAUSED;
    }

    public void stop() {
        mediaPlayer.stop();
        status = STATUS_STOPPED;
    }

    public void close() {
        stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public boolean isPlaying() {
        return status == STATUS_PLAYING;
    }

    public boolean isPaused() {
        return status == STATUS_PAUSED;
    }

    public boolean isStopped() { return status == STATUS_STOPPED; }
}
