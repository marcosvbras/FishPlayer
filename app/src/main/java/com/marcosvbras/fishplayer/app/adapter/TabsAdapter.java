package com.marcosvbras.fishplayer.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment;
    private List<String> listTitles;

    public TabsAdapter(FragmentManager fragmentManager, List<Fragment> listFragment, List<String> listTitles) {
        super(fragmentManager);
        this.listFragment = listFragment;
        this.listTitles = listTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
