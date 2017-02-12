package com.marcosvbras.fishplayer.app.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.marcosvbras.fishplayer.app.domain.Music;

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
            int titleColumn             = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn                = cursorMusic.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn            = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumIdColumn           = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int songDuration            = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int composerColumn          = cursorMusic.getColumnIndex(MediaStore.Audio.Media.COMPOSER);
            int dateAddedColumn         = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
            int dateModifiedColumn      = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED);
            int yearColumn              = cursorMusic.getColumnIndex(MediaStore.Audio.Media.YEAR);
            int sizeColumn              = cursorMusic.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int fileNameColumn          = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int albumColumn             = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int isAlarmColumn           = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_ALARM);
            int isMusicColumn           = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
            int isNotificationColumn    = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_NOTIFICATION);
            int isPodcastColumn         = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_PODCAST);
            int isRingtoneColumn        = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_RINGTONE);
            int trackNumberColumn       = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TRACK);
            int mimeTypeColumn          = cursorMusic.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

            // Add songs to list
            do {
                Music music = new Music();
                music.setMusicPath(cursorMusic.getString(cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATA)));
                music.setId(cursorMusic.getLong(idColumn));
                music.setAlbumId(cursorMusic.getLong(albumIdColumn));
                music.setTitle(cursorMusic.getString(titleColumn));
                music.setArtist(cursorMusic.getString(artistColumn));
                music.setDuration(Integer.parseInt(cursorMusic.getString(songDuration)));
                music.setComposer(cursorMusic.getString(composerColumn));
                music.setDataAdded(cursorMusic.getLong(dateAddedColumn));
                music.setDataModified(cursorMusic.getLong(dateModifiedColumn));
                music.setYear(cursorMusic.getInt(yearColumn));
                music.setSizeFile(cursorMusic.getLong(sizeColumn));
                music.setFileName(cursorMusic.getString(fileNameColumn));
                music.setAlbum(cursorMusic.getString(albumColumn));
                music.setAlarm(cursorMusic.getInt(isAlarmColumn) == 1 ? true : false);
                music.setMusic(cursorMusic.getInt(isMusicColumn) == 1 ? true : false);
                music.setNotification(cursorMusic.getInt(isNotificationColumn) == 1 ? true : false);
                music.setPodcast(cursorMusic.getInt(isPodcastColumn) == 1 ? true : false);
                music.setRingtone(cursorMusic.getInt(isRingtoneColumn) == 1 ? true : false);
                music.setTrackNumber(cursorMusic.getInt(trackNumberColumn));
                music.setMimeType(cursorMusic.getString(mimeTypeColumn));

                Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(music.getAlbumId())}, null);

                if (cursor.moveToFirst()) {
                    music.setCoverPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                }
                cursor.close();
                cursor = null;
                listMusic.add(music);
            } while (cursorMusic.moveToNext());
        }
        cursorMusic.close();
        cursorMusic = null;
        return listMusic;
    }
}
