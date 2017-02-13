package com.marcosvbras.fishplayer.app.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.marcosvbras.fishplayer.app.interfaces.OnMusicProgressChangeListener;

import java.io.IOException;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class FishPlayer {

    private int status;
    private MediaPlayer mediaPlayer;
    private String musicPath;
    private Context context;
    private MusicProgressRunnable musicProgressRunnable;

    public FishPlayer(Context context) {
        mediaPlayer = new MediaPlayer();
        status = Constants.STATUS_NEW;
        this.context = context;
    }

    public void play(String musicPath) {
        this.musicPath = musicPath;

        try{
            if(status == Constants.STATUS_NEW) {
                mediaPlayer.setDataSource(musicPath);
                mediaPlayer.prepare();
                mediaPlayer.start();

                if(musicProgressRunnable != null) {
                    if(musicProgressRunnable.isPaused())
                        musicProgressRunnable.resume();
                    else
                        musicProgressRunnable.run();
                }

            } else if(status == Constants.STATUS_PAUSED) {
                mediaPlayer.start();

                if(musicProgressRunnable != null && musicProgressRunnable.isPaused())
                    musicProgressRunnable.resume();
            }

            status = Constants.STATUS_PLAYING;
        } catch (IOException e) {

        }
    }

    public void prepareForNewMusic() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        status = Constants.STATUS_NEW;
    }

    public void pause() {
        mediaPlayer.pause();
        status = Constants.STATUS_PAUSED;

        if(musicProgressRunnable != null)
            musicProgressRunnable.pause();
    }

    public void reset() {
        mediaPlayer.reset();
        status = Constants.STATUS_NEW;
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        status = Constants.STATUS_STOPPED;
    }

    public void close() {
        stop();
        musicProgressRunnable.pause();
        musicProgressRunnable = null;
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void seekTo(int progressInMilisseconds) {
        mediaPlayer.seekTo(progressInMilisseconds);
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        if(mediaPlayer != null)
            mediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    public void setOnMusicProgressChangeListener(OnMusicProgressChangeListener onMusicProgressChangeListener) {
        if(onMusicProgressChangeListener != null) {
            musicProgressRunnable = new MusicProgressRunnable(mediaPlayer, onMusicProgressChangeListener);
        } else {
            musicProgressRunnable = null;
        }
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentProgress() {
        return mediaPlayer.getCurrentPosition();
    }

    public boolean isPlaying() {
        return status == Constants.STATUS_PLAYING;
    }

    public boolean isPaused() {
        return status == Constants.STATUS_PAUSED;
    }

    public boolean isStopped() { return status == Constants.STATUS_STOPPED; }
}
