package com.marcosvbras.fishplayer.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.FishApplication;
import com.marcosvbras.fishplayer.app.adapter.PlayListAdapter;
import com.marcosvbras.fishplayer.app.domain.Music;
import com.marcosvbras.fishplayer.app.interfaces.RecyclerViewTouchListener;
import com.marcosvbras.fishplayer.app.listener.RecyclerItemClickListener;
import com.marcosvbras.fishplayer.app.util.Constants;
import com.marcosvbras.fishplayer.app.util.MusicHelper;
import com.marcosvbras.fishplayer.app.util.PermissionUtils;

public class MainActivity extends AppCompatActivity implements RecyclerViewTouchListener {

    // Views
    private RecyclerView recyclerView;

    // Another objects
    private PlayListAdapter playListAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponents();
        loadMusicList();
    }

    private void loadComponents() {
        setSupportActionBar((Toolbar)findViewById(R.id.top_toolbar));
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
    }

    private void loadMusicList() {
        String[] permissoes = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE };
        PermissionUtils.validate(this, 0, permissoes);

        if(PermissionUtils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final Activity activity = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    FishApplication.listMusic = MusicHelper.discoverSongs(activity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showList();
                            progressBar.setVisibility(View.GONE);
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
        if(FishApplication.listMusic != null && FishApplication.listMusic.size() > 0) {
            playListAdapter = new PlayListAdapter(this, FishApplication.listMusic);
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
            recyclerView.setAdapter(playListAdapter);
        } else {
            ((TextView)findViewById(R.id.text_view_message)).setText(getString(R.string.no_music_found));
            findViewById(R.id.text_view_message).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Music music = playListAdapter.getMusicAt(position);
        FishApplication.currentMusicIndex = position;
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
