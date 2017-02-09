package com.marcosvbras.kiwiiplayer.app.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.provider.MediaStore;
import android.view.View;

import com.marcosvbras.kiwiiplayer.R;
import com.marcosvbras.kiwiiplayer.app.adapter.PlayListAdapter;
import com.marcosvbras.kiwiiplayer.app.domain.Music;
import com.marcosvbras.kiwiiplayer.app.interfaces.RecyclerViewTouchListener;
import com.marcosvbras.kiwiiplayer.app.listener.RecyclerItemClickListener;
import com.marcosvbras.kiwiiplayer.app.util.Constants;
import com.marcosvbras.kiwiiplayer.app.util.MusicHelper;
import com.marcosvbras.kiwiiplayer.app.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewTouchListener {

    // Views
    private RecyclerView recyclerView;

    // Another objects
    private List<Music> listMusic;
    private PlayListAdapter playListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponents();
    }

    private void loadComponents() {
        setSupportActionBar((Toolbar)findViewById(R.id.top_toolbar));
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] permissoes = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE };
        PermissionUtils.validate(this, 0, permissoes);

        if(PermissionUtils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            listMusic = MusicHelper.discoverSongs(this);
            playListAdapter = new PlayListAdapter(this, listMusic);
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
            recyclerView.setAdapter(playListAdapter);
        } else {
            // Solicita as permissões
            PermissionUtils.validate(this, 0, permissoes);
        }
    }

//    public void getMusicList(){
//        // Query external songs
//        ContentResolver contentResolver = getContentResolver();
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor cursorMusic = contentResolver.query(uri, null, null, null, null);
//
//        // Create list if has value
//        if(cursorMusic != null && cursorMusic.moveToFirst()){
//            // Get columns
//            int titleColumn     = cursorMusic.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int idColumn        = cursorMusic.getColumnIndex(MediaStore.Audio.Media._ID);
//            int artistColumn    = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ARTIST);
//            int albumColumn     = cursorMusic.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
//            int songDuration    = cursorMusic.getColumnIndex(MediaStore.Audio.Media.DURATION);
//
//            listMusic = new ArrayList<>();
//            Bitmap bitmapAlbumArt;
//
//            // Add songs to list
//            do {
//                Music music = new Music();
//                music.setMusicPath(cursorMusic.getString(cursorMusic.getColumnIndex(MediaStore.Audio.Media.DATA)));
//                music.setId(cursorMusic.getLong(idColumn));
//                music.setAlbumId(cursorMusic.getLong(albumColumn));
//                music.setTitle(cursorMusic.getString(titleColumn));
//                music.setArtist(cursorMusic.getString(artistColumn));
//                music.setDuration(Integer.parseInt(cursorMusic.getString(songDuration)));
//
//                Cursor cursor = getActivity().managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
//                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
//                        MediaStore.Audio.Albums._ID+ "=?",
//                        new String[] {String.valueOf(albumId)},
//                        null);
//
//                if (cursor.moveToFirst()) {
//                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//                    // do whatever you need to do
//                }
//
//                listMusic.add(music);
//            } while (cursorMusic.moveToNext());
//        }
//    }

    @Override
    public void onItemClickListener(View view, int position) {
        Music music = playListAdapter.getMusicAt(position);
        Intent intent = new Intent(this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_MUSIC, music);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongItemClickListener(View view, int position) {

    }
}
