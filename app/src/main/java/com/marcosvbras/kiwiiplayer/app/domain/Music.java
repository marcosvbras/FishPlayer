package com.marcosvbras.kiwiiplayer.app.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class Music implements Parcelable {

    private long id;
    private long albumId;
    private int duration;
    private String musicPath;
    private String coverPath;
    private String title;
    private String artist;

    public Music() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Parcelable section
     * */

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    protected Music(Parcel in) {
        id = in.readLong();
        albumId = in.readLong();
        duration = in.readInt();
        musicPath = in.readString();
        coverPath = in.readString();
        title = in.readString();
        artist = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(albumId);
        dest.writeInt(duration);
        dest.writeString(musicPath);
        dest.writeString(coverPath);
        dest.writeString(title);
        dest.writeString(artist);
    }
}
