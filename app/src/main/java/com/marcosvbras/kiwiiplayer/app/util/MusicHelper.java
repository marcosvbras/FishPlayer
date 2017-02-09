package com.marcosvbras.kiwiiplayer.app.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.marcosvbras.kiwiiplayer.app.domain.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosvbras on 08/02/17.
 */

public class MusicHelper {

    public static final List<Music> discoverSongs(Activity context) {
        // Query external songs
        List<Music> listMusic = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursorMusic = contentResolver.query(uri, null, null, null, null);

        // Create list if has value
        if(cursorMusic != null && cursorMusic.moveToFirst()){
            // Get columns
            int titleColumn     = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn        = cursorMusic.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn    = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn     = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int songDuration    = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DURATION);

            Bitmap bitmapAlbumArt;

            // Add songs to list
            do {
                Music music = new Music();
                music.setMusicPath(cursorMusic.getString(cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATA)));
                music.setId(cursorMusic.getLong(idColumn));
                music.setAlbumId(cursorMusic.getLong(albumColumn));
                music.setTitle(cursorMusic.getString(titleColumn));
                music.setArtist(cursorMusic.getString(artistColumn));
                music.setDuration(Integer.parseInt(cursorMusic.getString(songDuration)));

                Cursor cursor = context.managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(music.getAlbumId())}, null);

                if (cursor.moveToFirst()) {
                    music.setCoverPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                }

                cursor.close();

                listMusic.add(music);
            } while (cursorMusic.moveToNext());

            cursorMusic.close();
        }

        return listMusic;
    }

}
