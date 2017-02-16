package com.marcosvbras.fishplayer.app.domain;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

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

    public Album(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
        name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
        artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
        firstYear = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
        numberOfSongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
        albumArtPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
    }

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

    /**
     * Parcelable
     * */

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
