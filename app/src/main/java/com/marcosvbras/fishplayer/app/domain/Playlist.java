package com.marcosvbras.fishplayer.app.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class Playlist implements Parcelable {

    private int id;
    private String name;

    public Playlist() {}

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Playlist(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
