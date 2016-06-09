package com.rustaronline.mobile.rustartourism.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rustaronline.mobile.rustartourism.Activities.FirstPage;
import com.rustaronline.mobile.rustartourism.Activities.Search;

/**
 * Created by gio on 10/05/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            FirstPage firstPage = new FirstPage();
            return firstPage;
        }
        else if (position == 1) {
            Search search = new Search();
            return search;
        } else
            return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
