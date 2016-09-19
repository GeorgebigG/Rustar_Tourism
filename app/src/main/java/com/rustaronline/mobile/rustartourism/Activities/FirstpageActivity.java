package com.rustaronline.mobile.rustartourism.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rustaronline.mobile.rustartourism.Helper.ViewPagerAdapter;
import com.rustaronline.mobile.rustartourism.R;

public class FirstpageActivity extends AppCompatActivity {
    public static String Username;
    public static String Password;
    public static boolean destroyActivity = false;

    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    ViewPager viewPager;

    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("/   " + Username);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pages_view);
        tabLayout = (TabLayout) findViewById(R.id.rustarTabs);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.newTab();
        tabLayout.newTab();

        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_home_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_search_white_24dp);

        FirstPage.tabLayout = tabLayout;

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.logOut:
                Intent intent = new Intent(this, LoginActivity.class);
                LoginActivity.logOutClicked = true;
                destroyActivity = true;
                onBackPressed();
                startActivity(intent);
                destroyActivity = false;
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!destroyActivity) {
            new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.exit))
                    .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirstpageActivity.super.onBackPressed();
                        }
                    }).setNegativeButton(getResources().getString(R.string.No), null).create().show();
        } else {
            super.onBackPressed();
        }
    }
}
