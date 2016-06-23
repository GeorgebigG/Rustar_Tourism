package com.rustaronline.mobile.rustartourism.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.Helper.ViewPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FirstpageActivity extends AppCompatActivity {
    public static String Username;
    public static boolean destroyActivity = false;
    public static ProgressDialog pd;
    public static EditText checkIn, checkOut, nights, location, holtel, pax;
    public static int checkIn_year_x, checkIn_month_x,checkIn_day_x,checkInDialogId = 0;
    public static int checkOut_year_x, checkOut_month_x,checkOut_day_x,checkOutDialogId = 1;
    public static Button search;

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

        checkIn = (EditText) findViewById(R.id.checkIn);
        checkOut = (EditText) findViewById(R.id.checkOut);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        checkIn_day_x = calendar.get(Calendar.DAY_OF_MONTH);
        checkIn_month_x = calendar.get(Calendar.MONTH);
        checkIn_year_x = calendar.get(Calendar.YEAR);

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        checkOut_day_x = calendar.get(Calendar.DAY_OF_MONTH);
        checkOut_month_x = calendar.get(Calendar.MONTH);
        checkOut_year_x = calendar.get(Calendar.YEAR);

        viewPager = (ViewPager) findViewById(R.id.include2);
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
    protected void onStart() {
        super.onStart();
        LoginActivity.pd.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                     checkIn_year_x = year;
                    checkIn_month_x = monthOfYear;
                    checkIn_day_x = dayOfMonth;

                    checkIn_month_x++;

                    String day_xAsString = "";
                    String month_xAsString = "";

                    if (checkIn_day_x > 9)
                        day_xAsString = checkIn_day_x + "";
                    else if (checkIn_day_x < 9)
                        day_xAsString = "0" + checkIn_day_x;

                    if (checkIn_month_x > 9)
                        month_xAsString = checkIn_month_x + "";
                    else if (checkIn_month_x < 9)
                        month_xAsString = "0" + checkIn_month_x;

                    checkIn.setText(day_xAsString + "-" + month_xAsString + "-" +  checkIn_year_x);
                }
            };


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == checkInDialogId)
            return new DatePickerDialog(this, dateSetListener ,  checkIn_year_x, checkIn_month_x, checkIn_day_x);
        else if (id == checkOutDialogId)
            return new DatePickerDialog(this, dateSetListener ,  checkOut_year_x, checkOut_month_x, checkOut_day_x);
        return  null;
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

    public void checkInClickListener(View view) {
        showDialog(checkOutDialogId);
    }
}
