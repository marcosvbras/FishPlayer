package com.marcosvbras.fishplayer.app.activity;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
    private SearchView searchView;

    // Another Objects
    private List<String> listTitles;
    private List<Fragment> listFragments;
    private int pageSelected;
    private MusicsFragment musicsFragment;
    private ArtistsFragment artistsFragment;
    private AlbumsFragment albumsFragment;
    private PlaylistsFragment playlistsFragment;

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
        listTitles.add(getString(R.string.albums));
        listTitles.add(getString(R.string.playlists));

        listFragments = new ArrayList<>();
        musicsFragment = new MusicsFragment();
        artistsFragment = new ArtistsFragment();
        albumsFragment = new AlbumsFragment();
        playlistsFragment = new PlaylistsFragment();
        listFragments.add(musicsFragment);
        listFragments.add(artistsFragment);
        listFragments.add(albumsFragment);
        listFragments.add(playlistsFragment);
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
        slidingTabLayout.setOnPageChangeListener(onPageChangeListener());
    }

    private ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageSelected = position;
            }

            @Override
            public void onPageSelected(int position) {
                pageSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        MenuItem menuItemSearchView = menu.findItem(R.id.menu_item_search);
        searchView = (SearchView)menuItemSearchView.getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener());
        return true;
    }

    private SearchView.OnQueryTextListener onQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((ArtistsFragment)listFragments.get(pageSelected)).updateSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }
}
