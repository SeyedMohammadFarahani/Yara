package com.ansarbank.room.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ansarbank.room.Util.MyTab;

import java.util.ArrayList;

public class Pager extends FragmentPagerAdapter {

    ArrayList<MyTab> tabs;

    public Pager(FragmentManager fm, ArrayList<MyTab> fragments) {
        super(fm);
        this.tabs = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return tabs.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {

        return tabs.get(position).getString();
    }
}
