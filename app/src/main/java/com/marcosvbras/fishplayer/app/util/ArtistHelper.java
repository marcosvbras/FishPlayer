package com.marcosvbras.fishplayer.app.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.marcosvbras.fishplayer.app.domain.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosvbras on 16/02/2017.
 */

public class ArtistHelper {

    public static Artist findArtistById(Activity activity, long artistId) {
        Artist artist = null;
        ContentResolver contentResolver = activity.getContentResolver();

        String[] columns = new String[] { MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS, MediaStore.Audio.Artists.NUMBER_OF_TRACKS };

        Cursor cursor = contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                columns, MediaStore.Audio.Artists._ID + " = ?", new String[]{String.valueOf(artistId)}, null);

        if(cursor != null) {
            if(cursor.moveToFirst())
                artist = new Artist(cursor);

            cursor.close();
            cursor = null;
        }

        return artist;
    }

    public static List<Artist> discoverArtists(Activity activity, String orderByColumn) {
        List<Artist> listArtist = null;
        ContentResolver contentResolver = activity.getContentResolver();

        String[] columns = new String[] { MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS, MediaStore.Audio.Artists.NUMBER_OF_TRACKS };

        Cursor cursor = contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                columns, null, null, orderByColumn);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                listArtist = new ArrayList<>();

                do {
                    Artist artist = new Artist(cursor);
                    listArtist.add(artist);
                } while(cursor.moveToNext());
            }

            cursor.close();
            cursor = null;
        }

        return listArtist;
    }
}
