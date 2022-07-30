package com.ansarbank.room.Util;

import android.support.v4.app.Fragment;

public class MyTab {

    Fragment fragment;
    String string;

    public MyTab(Fragment fragment, String string) {
        this.fragment = fragment;
        this.string = string;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getString() {
        return string;
    }
}
