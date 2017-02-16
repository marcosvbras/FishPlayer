package com.marcosvbras.fishplayer.app.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.FishApplication;
import com.marcosvbras.fishplayer.app.activity.PlayerActivity;
import com.marcosvbras.fishplayer.app.adapter.MusicAdapter;
import com.marcosvbras.fishplayer.app.domain.SimpleMusic;
import com.marcosvbras.fishplayer.app.interfaces.OnRecyclerViewTouchListener;
import com.marcosvbras.fishplayer.app.listener.RecyclerItemClickListener;
import com.marcosvbras.fishplayer.app.util.Constants;
import com.marcosvbras.fishplayer.app.util.MusicHelper;
import com.marcosvbras.fishplayer.app.util.PermissionUtils;

import java.util.List;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class MusicsFragment extends Fragment implements OnRecyclerViewTouchListener {

    // Views
    private RecyclerView recyclerView;

    // Another objects
    private MusicAdapter musicAdapter;
    private ProgressBar progressBar;
    private List<SimpleMusic> listSimpleMusic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_musics, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));
        progressBar = (ProgressBar)getView().findViewById(R.id.progress_bar);
        loadMusicList();
    }

    private void loadMusicList() {
        String[] permissoes = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE };
        PermissionUtils.validate(getActivity(), 0, permissoes);

        if(PermissionUtils.checkPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final Activity activity = getActivity();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    listSimpleMusic = MusicHelper.discoverSimpleMusics(activity, MediaStore.Audio.Media.TITLE);
                    getActivity().runOnUiThread(new Runnable() {
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
            PermissionUtils.validate(getActivity(), 0, permissoes);
        }
    }

    private void showList() {
        if(listSimpleMusic != null && listSimpleMusic.size() > 0) {
            musicAdapter = new MusicAdapter(getActivity(), listSimpleMusic);
            recyclerView.setAdapter(musicAdapter);
        } else {
            ((TextView)getView().findViewById(R.id.text_view_message)).setText(getString(R.string.no_music_found));
            getView().findViewById(R.id.text_view_message).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SimpleMusic simpleMusic = musicAdapter.getMusicAt(position);
        FishApplication.currentMusicList = listSimpleMusic;
        FishApplication.currentMusicIndex = position;
        Intent intent = new Intent(getContext(), PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_MUSIC, simpleMusic);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}
