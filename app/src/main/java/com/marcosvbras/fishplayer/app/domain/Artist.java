package com.marcosvbras.fishplayer.app.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class Artist implements Parcelable {

    private long id;
    private String name;
    private int numberOfAlbums;
    private int numberOfTracks;

    public Artist() {}

    public Artist(long id, String name, int numberOfAlbums, int numberOfTracks) {
        this.id = id;
        this.name = name;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfTracks = numberOfTracks;
    }

    protected Artist(Parcel in) {
        id = in.readLong();
        name = in.readString();
        numberOfAlbums = in.readInt();
        numberOfTracks = in.readInt();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(numberOfAlbums);
        dest.writeInt(numberOfTracks);
    }
}
