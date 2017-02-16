package com.marcosvbras.fishplayer.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.FishApplication;
import com.marcosvbras.fishplayer.app.adapter.MusicAdapter;
import com.marcosvbras.fishplayer.app.domain.Album;
import com.marcosvbras.fishplayer.app.domain.Music;
import com.marcosvbras.fishplayer.app.domain.SimpleMusic;
import com.marcosvbras.fishplayer.app.fragments.AlbumsFragment;
import com.marcosvbras.fishplayer.app.interfaces.OnRecyclerViewTouchListener;
import com.marcosvbras.fishplayer.app.listener.RecyclerItemClickListener;
import com.marcosvbras.fishplayer.app.util.Constants;
import com.marcosvbras.fishplayer.app.util.MusicHelper;
import com.marcosvbras.fishplayer.app.util.PermissionUtils;

import java.util.List;

public class AlbumActivity extends AppCompatActivity implements OnRecyclerViewTouchListener {

    // Views
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private TextView textViewArtist;

    // Another Objects
    private Album album;
    private List<SimpleMusic> listSimpleMusic;
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
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_play);
        floatingActionButton.setOnClickListener(onPlayButtonClick());
        textViewArtist = (TextView)findViewById(R.id.text_view_artist);
    }

    private View.OnClickListener onPlayButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleMusic simpleMusic = musicAdapter.getMusicAt(0);
                FishApplication.currentMusicList = listSimpleMusic;
                FishApplication.currentMusicIndex = 0;
                Intent intent = new Intent(getBaseContext(), PlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_MUSIC, simpleMusic);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().getExtras().getParcelable(AlbumsFragment.KEY) != null) {
            album = getIntent().getExtras().getParcelable(AlbumsFragment.KEY);

            if(album.getName() != null && !album.getName().equals("") && !album.getName().equals("0"))
                getSupportActionBar().setTitle(album.getName());
            else
                getSupportActionBar().setTitle(getString(R.string.unknown_album));

            if(album.getArtist() != null && !album.getArtist().equals("") && !album.getArtist().equals("0"))
                textViewArtist.setText(album.getArtist());
            else
                textViewArtist.setText(getString(R.string.unknown_artist));

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
                    listSimpleMusic = MusicHelper.discoverSimpleMusicsByAlbumId(context, album.getId(), MediaStore.Audio.Media.TITLE);
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
        if(listSimpleMusic != null && listSimpleMusic.size() > 0) {
            musicAdapter = new MusicAdapter(this, listSimpleMusic);
            recyclerView.setAdapter(musicAdapter);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SimpleMusic simpleMusic = musicAdapter.getMusicAt(position);
        FishApplication.currentMusicList = listSimpleMusic;
        FishApplication.currentMusicIndex = position;
        Intent intent = new Intent(this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_MUSIC, simpleMusic);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}
