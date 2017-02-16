package com.marcosvbras.fishplayer.app.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcosvbras on 15/02/2017.
 */

public class SimpleMusic implements Parcelable {

    private long id;
    private int duration;
    private String album;
    private String artist;
    private String musicPath;
    private String title;

    public SimpleMusic() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Parcelable Section
     * */

    protected SimpleMusic(Parcel in) {
        id = in.readLong();
        duration = in.readInt();
        album = in.readString();
        artist = in.readString();
        musicPath = in.readString();
        title = in.readString();
    }

    public static final Creator<SimpleMusic> CREATOR = new Creator<SimpleMusic>() {
        @Override
        public SimpleMusic createFromParcel(Parcel in) {
            return new SimpleMusic(in);
        }

        @Override
        public SimpleMusic[] newArray(int size) {
            return new SimpleMusic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(duration);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(musicPath);
        dest.writeString(title);
    }
}
