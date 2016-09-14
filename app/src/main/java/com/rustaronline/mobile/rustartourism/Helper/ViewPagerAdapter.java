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

    private static final int HOME_PAGE = 0;
    private static final int SEARCH_PAGE = 1;

    private static final int AMOUNT_OF_PAGES = 2;

    @Override
    public Fragment getItem(int position) {
        if (position == HOME_PAGE) {
            FirstPage firstPage = new FirstPage();
            return firstPage;
        }
        else if (position == SEARCH_PAGE) {
            Search search = new Search();
            return search;
        } else
            return null;
    }

    @Override
    public int getCount() {
        return AMOUNT_OF_PAGES;
    }
}
