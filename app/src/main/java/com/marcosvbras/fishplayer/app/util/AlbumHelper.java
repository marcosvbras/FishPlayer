package com.marcosvbras.fishplayer.app.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.marcosvbras.fishplayer.app.domain.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosvbras on 16/02/2017.
 */

public class AlbumHelper {

    public static Album findAlbumById(Activity activity, long albumId) {
        Album album = null;
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Albums._ID + " = ?", new String[] {String.valueOf(albumId)}, null);

        if(cursor != null) {

            if(cursor.moveToFirst())
                album = new Album(cursor);

            cursor.close();
            cursor = null;
        }

        return album;
    }

    public static List<Album> discoverAlbums(Activity activity, String orderByColumn) {
        List<Album> listAlbum = null;
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, orderByColumn);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                listAlbum = new ArrayList<>();

                do {
                    Album album = new Album(cursor);
                    listAlbum.add(album);
                } while (cursor.moveToNext());
            }

            cursor.close();
            cursor = null;
        }

        return listAlbum;
    }
}
