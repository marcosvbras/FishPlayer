package com.marcosvbras.fishplayer.app.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class Music implements Parcelable {

    private long albumId;
    private long artistId;
    private long dataAdded; // in seconds
    private long dataModified;
    private long id;
    private long sizeFile; // in bytes
    private int duration;
    private int trackNumber;
    private int year;
    private String album;
    private String artist;
    private String composer;
    private String albumArtPath;
    private String fileName;
    private String mimeType;
    private String musicPath;
    private String title;
    private boolean isAlarm;
    private boolean isMusic;
    private boolean isNotification;
    private boolean isPodcast;
    private boolean isRingtone;
    private byte[] filePicture;

    //BitmapFactory.decodeByteArray(data, 0, data.length)

    public Music() { }

    /**
     * Getters and Setters
     * */

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getAlbumArtPath() {
        return albumArtPath;
    }

    public void setAlbumArtPath(String albumArtPath) {
        this.albumArtPath = albumArtPath;
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

    public byte[] getFilePicture() {
        return filePicture;
    }

    public void setFilePicture(byte[] filePicture) {
        this.filePicture = filePicture;
    }

    /**
     * Parcelable
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
        albumId = in.readLong();
        artistId = in.readLong();
        dataAdded = in.readLong();
        dataModified = in.readLong();
        id = in.readLong();
        sizeFile = in.readLong();
        duration = in.readInt();
        trackNumber = in.readInt();
        year = in.readInt();
        album = in.readString();
        artist = in.readString();
        composer = in.readString();
        albumArtPath = in.readString();
        fileName = in.readString();
        mimeType = in.readString();
        musicPath = in.readString();
        title = in.readString();
        isAlarm = in.readInt() == 1 ? true : false;
        isMusic = in.readInt() == 1 ? true : false;
        isNotification = in.readInt() == 1 ? true : false;
        isPodcast = in.readInt() == 1 ? true : false;
        isRingtone = in.readInt() == 1 ? true : false;
        filePicture = new byte[in.readInt()];
        in.readByteArray(filePicture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(albumId);
        dest.writeLong(artistId);
        dest.writeLong(dataAdded);
        dest.writeLong(dataModified);
        dest.writeLong(id);
        dest.writeLong(sizeFile);
        dest.writeInt(duration);
        dest.writeInt(trackNumber);
        dest.writeInt(year);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(composer);
        dest.writeString(albumArtPath);
        dest.writeString(fileName);
        dest.writeString(mimeType);
        dest.writeString(musicPath);
        dest.writeString(title);
        dest.writeInt(isAlarm ? 1 : 0);
        dest.writeInt(isMusic ? 1 : 0);
        dest.writeInt(isNotification ? 1 : 0);
        dest.writeInt(isPodcast ? 1 : 0);
        dest.writeInt(isRingtone ? 1 : 0);
        dest.writeInt(filePicture.length);
        dest.writeByteArray(filePicture);
    }
}
