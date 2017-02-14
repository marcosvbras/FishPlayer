package com.marcosvbras.fishplayer.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.adapter.MusicAdapter;
import com.marcosvbras.fishplayer.app.domain.Album;
import com.marcosvbras.fishplayer.app.domain.Music;
import com.marcosvbras.fishplayer.app.fragments.AlbumsFragment;
import com.marcosvbras.fishplayer.app.interfaces.OnRecyclerViewTouchListener;
import com.marcosvbras.fishplayer.app.listener.RecyclerItemClickListener;
import com.marcosvbras.fishplayer.app.util.MusicHelper;
import com.marcosvbras.fishplayer.app.util.PermissionUtils;

import java.util.List;

public class AlbumActivity extends AppCompatActivity implements OnRecyclerViewTouchListener {

    // Views
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView imageView;

    // Another Objects
    private Album album;
    private List<Music> listMusics;
    private MusicAdapter musicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_album);
        loadComponents();
    }

    private void loadComponents() {
        setSupportActionBar((Toolbar)findViewById(R.id.top_toolbar));
        imageView = (ImageView)findViewById(R.id.image_view_app_bar);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().getExtras().getParcelable(AlbumsFragment.KEY) != null) {
            album = getIntent().getExtras().getParcelable(AlbumsFragment.KEY);
            getSupportActionBar().setTitle(album.getName());
            loadAlbumArt();
            loadMusics();
        }
    }

    private void loadAlbumArt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeFile(album.getAlbumArtPath());
                if(bitmap != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        }).start();
    }

    private void loadMusics() {
        String[] permissoes = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE };
        PermissionUtils.validate(this, 0, permissoes);
        final Activity context = this;
        if(PermissionUtils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    listMusics = MusicHelper.loadListByAlbumId(context, album.getId(), MediaStore.Audio.Albums.ALBUM);
//                    listMusics = MusicHelper.discoverSongs(context, MediaStore.Audio.Albums.ALBUM);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showList();
                            //progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }).start();
        } else {
            // Solicita as permissões
            PermissionUtils.validate(this, 0, permissoes);
        }
    }

    private void showList() {
        if(listMusics != null && listMusics.size() > 0) {
            musicAdapter = new MusicAdapter(this, listMusics);
            recyclerView.setAdapter(musicAdapter);
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {

    }

    @Override
    public void onLongItemClickListener(View view, int position) {

    }
}
