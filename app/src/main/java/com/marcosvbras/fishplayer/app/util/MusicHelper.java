package com.marcosvbras.fishplayer.app.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import com.marcosvbras.fishplayer.app.domain.Album;
import com.marcosvbras.fishplayer.app.domain.Artist;
import com.marcosvbras.fishplayer.app.domain.Music;
import com.marcosvbras.fishplayer.app.domain.SimpleMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosvbras on 08/02/17.
 */

public class MusicHelper {

    public static List<SimpleMusic> discoverSimpleMusics(Activity activity, String orderByColumn) {
        List<SimpleMusic> listSimpleMusic = null;
        ContentResolver contentResolver = activity.getContentResolver();

        String[] colums = new String[] { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE };

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, colums, null, null, orderByColumn);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                listSimpleMusic = new ArrayList<>();

                do {
                    SimpleMusic simpleMusic = new SimpleMusic();
                    simpleMusic.setId(cursor.getLong(cursor.getColumnIndex(colums[0])));
                    simpleMusic.setDuration(cursor.getInt(cursor.getColumnIndex(colums[1])));
                    simpleMusic.setAlbum(cursor.getString(cursor.getColumnIndex(colums[2])));
                    simpleMusic.setArtist(cursor.getString(cursor.getColumnIndex(colums[3])));
                    simpleMusic.setMusicPath(cursor.getString(cursor.getColumnIndex(colums[4])));
                    simpleMusic.setTitle(cursor.getString(cursor.getColumnIndex(colums[5])));
                    listSimpleMusic.add(simpleMusic);
                } while(cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
        }

        return listSimpleMusic;
    }

    public static List<SimpleMusic> discoverSimpleMusicsByAlbumId(Activity activity, long albumId, String orderByColumn) {
        List<SimpleMusic> listSimpleMusic = null;
        ContentResolver contentResolver = activity.getContentResolver();

        String[] colums = new String[] { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE };

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, colums,
                MediaStore.Audio.Media.ALBUM_ID + " = ?", new String[] { String.valueOf(albumId) }, orderByColumn);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                listSimpleMusic = new ArrayList<>();

                do {
                    SimpleMusic simpleMusic = new SimpleMusic();
                    simpleMusic.setId(cursor.getLong(cursor.getColumnIndex(colums[0])));
                    simpleMusic.setDuration(cursor.getInt(cursor.getColumnIndex(colums[1])));
                    simpleMusic.setAlbum(cursor.getString(cursor.getColumnIndex(colums[2])));
                    simpleMusic.setArtist(cursor.getString(cursor.getColumnIndex(colums[3])));
                    simpleMusic.setMusicPath(cursor.getString(cursor.getColumnIndex(colums[4])));
                    simpleMusic.setTitle(cursor.getString(cursor.getColumnIndex(colums[5])));
                    listSimpleMusic.add(simpleMusic);
                } while(cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
        }

        return listSimpleMusic;
    }

    public static List<Music> discoverMusics(Activity activity, String orderByColumn) {
        List<Music> listMusics = null;
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, orderByColumn);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                listMusics = new ArrayList<>();

                do {
                    Artist artist = ArtistHelper.findArtistById(activity,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));
                    Album album = AlbumHelper.findAlbumById(activity,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                    listMusics.add(new Music(cursor, artist, album));
                } while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
        }

        return listMusics;
    }

    public static Music findMusicById(Activity activity, long musicId) {
        Music music = null;
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media._ID + " = ?", new String[] { String.valueOf(musicId) }, null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    Artist artist = ArtistHelper.findArtistById(activity,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));
                    Album album = AlbumHelper.findAlbumById(activity,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                    music = new Music(cursor, artist, album);
                } while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
        }

        return music;
    }

    public static byte[] getSpecificFilePicture(String musicPath) {
        MediaMetadataRetriever mediaMetaDataRetriever = new MediaMetadataRetriever();
        mediaMetaDataRetriever.setDataSource(musicPath);
        return mediaMetaDataRetriever.getEmbeddedPicture();
    }
}
