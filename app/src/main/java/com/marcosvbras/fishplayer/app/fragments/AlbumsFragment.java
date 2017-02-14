package com.marcosvbras.fishplayer.app.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.activity.AlbumActivity;
import com.marcosvbras.fishplayer.app.adapter.AlbumAdapter;
import com.marcosvbras.fishplayer.app.domain.Album;
import com.marcosvbras.fishplayer.app.interfaces.OnRecyclerViewTouchListener;
import com.marcosvbras.fishplayer.app.listener.RecyclerItemClickListener;
import com.marcosvbras.fishplayer.app.util.MusicHelper;
import com.marcosvbras.fishplayer.app.util.PermissionUtils;

import java.util.List;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class AlbumsFragment extends Fragment implements OnRecyclerViewTouchListener {

    // Views
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AlbumAdapter albumAdapter;

    // Another objects
    private List<Album> listAlbums;
    public static final String KEY = "album";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = (ProgressBar)getView().findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));
        loadList();
    }

    private void loadList() {
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
                    listAlbums = MusicHelper.discoverAlbums(activity, MediaStore.Audio.Albums.ALBUM);
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
        if(listAlbums != null && listAlbums.size() > 0) {
            albumAdapter = new AlbumAdapter(listAlbums, getActivity());
            recyclerView.setAdapter(albumAdapter);
        } else {
            ((TextView)getView().findViewById(R.id.text_view_message)).setText(getString(R.string.no_album_found));
            getView().findViewById(R.id.text_view_message).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Album album = albumAdapter.getItemAt(position);
        Intent intent = new Intent(getActivity(), AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, album);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongItemClickListener(View view, int position) {

    }
}
