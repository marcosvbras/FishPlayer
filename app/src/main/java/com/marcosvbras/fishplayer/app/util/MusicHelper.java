package com.marcosvbras.fishplayer.app.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.marcosvbras.fishplayer.app.domain.Album;
import com.marcosvbras.fishplayer.app.domain.Artist;
import com.marcosvbras.fishplayer.app.domain.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcosvbras on 08/02/17.
 */

public class MusicHelper {

    public static List<Music> discoverSongs(Activity context, String orderByColumn) {
        // Query external songs
        List<Music> listMusic = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorMusic = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, orderByColumn);

        // Create list if has value
        if (cursorMusic != null) {
            if(cursorMusic.moveToFirst()) {
                // Get columns
                int dataColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATA);
                int titleColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumIdColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int songDuration = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int composerColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.COMPOSER);
                int dateAddedColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
                int dateModifiedColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED);
                int yearColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.YEAR);
                int sizeColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int fileNameColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int albumColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int isAlarmColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_ALARM);
                int isMusicColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
                int isNotificationColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_NOTIFICATION);
                int isPodcastColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_PODCAST);
                int isRingtoneColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_RINGTONE);
                int trackNumberColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TRACK);
                int mimeTypeColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

                // Add songs to list
                do {
                    Music music = new Music();
                    music.setMusicPath(cursorMusic.getString(dataColumn));
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
                    music.setAlbumArtPath(MusicHelper.getAlbumArt(context, music.getAlbumId()));

                    listMusic.add(music);
                } while (cursorMusic.moveToNext());
            }
            cursorMusic.close();
            cursorMusic = null;
        }

        return listMusic;
    }

    public static String getAlbumArt(Activity context, long albumId) {
        ContentResolver contentResolver = context.getContentResolver();
        String albumArt = null;

        Cursor cursorAlbum = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Albums.ALBUM_ART },
                MediaStore.Audio.Albums._ID + " = ?",
                new String[] { String.valueOf(albumId) }, null);

        if(cursorAlbum != null) {
            if (cursorAlbum.moveToFirst())
                albumArt = cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

            cursorAlbum.close();
            cursorAlbum = null;
        }

        return albumArt;
    }

    public static final List<Artist> discoverArtists(Activity context, String orderByColumn) {
        List<Artist> listArtist = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // Colunas de Artista
        String[] columns = new String[] { MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS, MediaStore.Audio.Artists.NUMBER_OF_TRACKS };

        Cursor cursorArtist = contentResolver.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, columns, null, null, orderByColumn);

        if(cursorArtist != null) {
            if(cursorArtist.moveToFirst()) {
                do {
                    Artist artist = new Artist();
                    artist.setId(cursorArtist.getInt(cursorArtist.getColumnIndex(columns[0])));
                    artist.setName(cursorArtist.getString(cursorArtist.getColumnIndex(columns[1])));
                    artist.setNumberOfAlbums(cursorArtist.getInt(cursorArtist.getColumnIndex(columns[2])));
                    artist.setNumberOfTracks(cursorArtist.getInt(cursorArtist.getColumnIndex(columns[3])));
                    listArtist.add(artist);
                } while(cursorArtist.moveToNext());
            }

            cursorArtist.close();
            cursorArtist = null;
        }

        return listArtist;
    }

    public static List<Album> discoverAlbums(Activity context, String orderByColumn) {
        List<Album> listAlbums = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursorAlbum = contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, orderByColumn);

        if(cursorAlbum != null) {
            if(cursorAlbum.moveToFirst()) {
                do {
                    Album album = new Album();
                    album.setId(cursorAlbum.getInt(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums._ID)));
                    album.setName(cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                    album.setArtist(cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
                    album.setFirstYear(cursorAlbum.getInt(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR)));
                    album.setNumberOfSongs(cursorAlbum.getInt(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
                    album.setAlbumArtPath(cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));

                    listAlbums.add(album);
                } while(cursorAlbum.moveToNext());
            }

            cursorAlbum.close();
            cursorAlbum = null;
        }

        return listAlbums;
    }

    public static List<Music> loadListByAlbumId(Activity context, long albumId, String orderByColumn) {
        List<Music> listMusics = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorMusic = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.ALBUM_ID + " = ?", new String[] { String.valueOf(albumId) }, orderByColumn);

        if(cursorMusic != null) {
            if(cursorMusic.moveToFirst()) {
                // Get columns
                int dataColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATA);
                int titleColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumIdColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int songDuration = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int composerColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.COMPOSER);
                int dateAddedColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
                int dateModifiedColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED);
                int yearColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.YEAR);
                int sizeColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int fileNameColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int albumColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int isAlarmColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_ALARM);
                int isMusicColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
                int isNotificationColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_NOTIFICATION);
                int isPodcastColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_PODCAST);
                int isRingtoneColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.IS_RINGTONE);
                int trackNumberColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TRACK);
                int mimeTypeColumn = cursorMusic.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

                // Add songs to list
                do {
                    Music music = new Music();
                    music.setMusicPath(cursorMusic.getString(dataColumn));
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
                    music.setAlbumArtPath(MusicHelper.getAlbumArt(context, music.getAlbumId()));

                    listMusics.add(music);
                } while (cursorMusic.moveToNext());
            }
            cursorMusic.close();
            cursorMusic = null;
        }

        return listMusics;
    }
}
