package com.marcosvbras.fishplayer.app.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class Album implements Parcelable {

    private long id;
    private String albumArtPath;
    private String artist;
    private String name;
    private int firstYear;
    private int numberOfSongs;

    public Album() {}

    public Album(long id, String albumArtPath, String artist, String name, int firstYear, int numberOfSongs) {
        this.id = id;
        this.albumArtPath = albumArtPath;
        this.artist = artist;
        this.name = name;
        this.firstYear = firstYear;
        this.numberOfSongs = numberOfSongs;
    }

    protected Album(Parcel in) {
        id = in.readLong();
        albumArtPath = in.readString();
        artist = in.readString();
        name = in.readString();
        firstYear = in.readInt();
        numberOfSongs = in.readInt();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbumArtPath() {
        return albumArtPath;
    }

    public void setAlbumArtPath(String albumArtPath) {
        this.albumArtPath = albumArtPath;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(albumArtPath);
        dest.writeString(artist);
        dest.writeString(name);
        dest.writeInt(firstYear);
        dest.writeInt(numberOfSongs);
    }
}
