package com.marcosvbras.fishplayer.app.domain;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class Music implements Parcelable {

    private long dataAdded; // in seconds
    private long dataModified;
    private long id;
    private long sizeFile; // in bytes
    private long duration;
    private int trackNumber;
    private int year;
    private String composer;
    private String fileName;
    private String mimeType;
    private String musicPath;
    private String title;
    private boolean isAlarm;
    private boolean isMusic;
    private boolean isNotification;
    private boolean isPodcast;
    private boolean isRingtone;
    private Album album;
    private Artist artist;

    //BitmapFactory.decodeByteArray(data, 0, data.length)

    public Music() {}

    public Music(Cursor cursor, Artist artist, Album album) {
        if(cursor != null) {
            musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
            dataAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
            dataModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED));
            year = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
            sizeFile = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            isAlarm = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_ALARM)) == 1 ? true : false;
            isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)) == 1 ? true : false;
            isNotification = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_NOTIFICATION)) == 1 ? true : false;
            isPodcast = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_PODCAST)) == 1 ? true : false;
            isRingtone = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_RINGTONE)) == 1 ? true : false;
            trackNumber = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
            mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
            this.artist = artist;
            this.album = album;
        }
    }

    /**
     * Getters and Setters
     * */

    public long getDataAdded() {
        return dataAdded;
    }

    public void setDataAdded(long dataAdded) {
        this.dataAdded = dataAdded;
    }

    public long getDataModified() {
        return dataModified;
    }

    public void setDataModified(long dataModified) {
        this.dataModified = dataModified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(long sizeFile) {
        this.sizeFile = sizeFile;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public void setMusic(boolean music) {
        isMusic = music;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }

    public boolean isPodcast() {
        return isPodcast;
    }

    public void setPodcast(boolean podcast) {
        isPodcast = podcast;
    }

    public boolean isRingtone() {
        return isRingtone;
    }

    public void setRingtone(boolean ringtone) {
        isRingtone = ringtone;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Parcelable
     * */

    protected Music(Parcel in) {
        dataAdded = in.readLong();
        dataModified = in.readLong();
        id = in.readLong();
        sizeFile = in.readLong();
        duration = in.readLong();
        trackNumber = in.readInt();
        year = in.readInt();
        composer = in.readString();
        fileName = in.readString();
        mimeType = in.readString();
        musicPath = in.readString();
        title = in.readString();
        isAlarm = in.readByte() != 0;
        isMusic = in.readByte() != 0;
        isNotification = in.readByte() != 0;
        isPodcast = in.readByte() != 0;
        isRingtone = in.readByte() != 0;
        album = in.readParcelable(Album.class.getClassLoader());
        artist = in.readParcelable(Artist.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dataAdded);
        dest.writeLong(dataModified);
        dest.writeLong(duration);
        dest.writeLong(id);
        dest.writeLong(sizeFile);
        dest.writeInt(trackNumber);
        dest.writeInt(year);
        dest.writeString(composer);
        dest.writeString(fileName);
        dest.writeString(mimeType);
        dest.writeString(musicPath);
        dest.writeString(title);
        dest.writeInt(isAlarm ? 1 : 0);
        dest.writeInt(isMusic ? 1 : 0);
        dest.writeInt(isNotification ? 1 : 0);
        dest.writeInt(isPodcast ? 1 : 0);
        dest.writeInt(isRingtone ? 1 : 0);
        dest.writeParcelable(album, flags);
        dest.writeParcelable(artist, flags);
    }
}
