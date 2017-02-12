package com.marcosvbras.fishplayer.app.activity;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.adapter.TabsAdapter;
import com.marcosvbras.fishplayer.app.fragments.AlbumsFragment;
import com.marcosvbras.fishplayer.app.fragments.ArtistsFragment;
import com.marcosvbras.fishplayer.app.fragments.MusicsFragment;
import com.marcosvbras.fishplayer.app.fragments.PlaylistsFragment;
import com.marcosvbras.fishplayer.app.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Views
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    // Another Objects
    private List<String> listTitles;
    private List<Fragment> listFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateLists();
        loadComponents();
    }

    private void populateLists() {
        listTitles = new ArrayList<>();
        listTitles.add(getString(R.string.musics));
        listTitles.add(getString(R.string.artists));
        listTitles.add(getString(R.string.albuns));
        listTitles.add(getString(R.string.playlists));

        listFragments = new ArrayList<>();
        listFragments.add(new MusicsFragment());
        listFragments.add(new ArtistsFragment());
        listFragments.add(new AlbumsFragment());
        listFragments.add(new PlaylistsFragment());
    }

    private void loadComponents() {
        setSupportActionBar((Toolbar)findViewById(R.id.top_toolbar));
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tab_layout);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), listFragments, listTitles));
        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent), ContextCompat.getColor(this, R.color.colorAccent));
        slidingTabLayout.setTitleColor(ContextCompat.getColor(this, android.R.color.white));
        slidingTabLayout.setViewPager(viewPager);
    }
}
