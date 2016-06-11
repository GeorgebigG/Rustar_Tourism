package com.rustaronline.mobile.rustartourism.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Hotel;
import com.rustaronline.mobile.rustartourism.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {
    public static Hotel hotel;
    TextView hotelName, description, date, price;
    RatingBar hotelClass;
    TableLayout tLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("/   " + FirstpageActivity.Username);
        setSupportActionBar(toolbar);

        hotelName = (TextView) findViewById(R.id.HotelName);
        description = (TextView) findViewById(R.id.Description);
        date = (TextView) findViewById(R.id.Date);
        price = (TextView) findViewById(R.id.Price);
        hotelClass = (RatingBar) findViewById(R.id.HotelClass);
        tLayout = (TableLayout) findViewById(R.id.mainTableLayout);
        hotelName.setText(hotel.getName());
        description.setText(hotel.getDescription());
        date.setText(hotel.getCheckInDate() + " - " + hotel.getCheckOutDate() + " (" + hotel.getAmountOfNights() + " nights).");
        price.setText(getResources().getString(R.string.Dollar) + (hotel.getPrice() * hotel.getAmountOfNights()));
        hotelClass.setRating(hotel.getStar());

        createTable();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void createTable() {
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        TableRow tableRow;
        TableLayout.LayoutParams tParams;
        TableRow.LayoutParams lParams;

        tableRow = new TableRow(this);
        tParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        int column = 1;
        final Calendar firstWeekDay = hotel.getCheckInCal();
        switch (hotel.getCheckInDayOfWeek()) {
            case Calendar.MONDAY:
                column = 1;
                break;
            case Calendar.TUESDAY:
                firstWeekDay.add(Calendar.DAY_OF_YEAR, -1);
                column = 2;
                break;
            case Calendar.WEDNESDAY:
                firstWeekDay.add(Calendar.DAY_OF_YEAR, -2);
                column = 3;
                break;
            case Calendar.THURSDAY:
                firstWeekDay.add(Calendar.DAY_OF_YEAR, -3);
                column = 4;
                break;
            case Calendar.FRIDAY:
                firstWeekDay.add(Calendar.DAY_OF_YEAR, -4);
                column = 5;
                break;
            case Calendar.SATURDAY:
                firstWeekDay.add(Calendar.DAY_OF_YEAR, -5);
                column = 6;
                break;
            case Calendar.SUNDAY:
                firstWeekDay.add(Calendar.DAY_OF_YEAR, -6);
                column = 7;
                break;

            default:
                break;
        }

        final Calendar lastWeekDay = (Calendar) firstWeekDay.clone();
        lastWeekDay.add(Calendar.DAY_OF_YEAR, 6);

        TextView dateTextView = new TextView(this);
        dateTextView.setText(formater.format(firstWeekDay.getTime()) + " - " + formater.format((lastWeekDay.getTime())));
        dateTextView.setTextSize(15);
        dateTextView.setBackground(getResources().getDrawable(R.drawable.border));
        dateTextView.setTextColor(getResources().getColor(R.color.black));
        lParams = new TableRow.LayoutParams(0);
        tableRow.addView(dateTextView, lParams);

        Calendar calendar = hotel.getCheckInCal();

        int day = (int) ((calendar.getTimeInMillis() - firstWeekDay.getTimeInMillis()) / 1000 / 60 / 60 / 24);
        if (day > 0)
            for (int i = 1; i <= day; i++) {
                TextView emptyText = new TextView(this);
                emptyText.setBackground(getResources().getDrawable(R.drawable.border));
                emptyText.setText("");
                emptyText.setTextSize(15);
                lParams = new TableRow.LayoutParams(i);
                tableRow.addView(emptyText, lParams);
            }

        for (int i = 0; i < hotel.getAmountOfNights(); i++) {
            TextView priceText = new TextView(this);
            priceText.setBackground(getResources().getDrawable(R.drawable.border));
            priceText.setTextColor(getResources().getColor(R.color.black));
            priceText.setTextSize(15);
            priceText.setText(getResources().getString(R.string.Dollar) + hotel.getPrice() + ", " + hotel.getMeal());
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            lParams = new TableRow.LayoutParams(column);
            column++;
            tableRow.addView(priceText, lParams);

            if (column == 8 && i+1 != hotel.getAmountOfNights()) {
                tLayout.addView(tableRow, tParams);

                tableRow = new TableRow(this);
                tParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                firstWeekDay.add(Calendar.DAY_OF_YEAR, 7);
                lastWeekDay.add(Calendar.DAY_OF_YEAR, 7);

                dateTextView = new TextView(this);
                dateTextView.setText(formater.format(firstWeekDay.getTime()) + " - " + formater.format((lastWeekDay.getTime())));
                dateTextView.setTextSize(15);
                dateTextView.setBackground(getResources().getDrawable(R.drawable.border));
                dateTextView.setTextColor(getResources().getColor(R.color.black));
                lParams = new TableRow.LayoutParams(0);
                tableRow.addView(dateTextView, lParams);

                column = 1;
            }
        }

        day = (int) ((lastWeekDay.getTimeInMillis() - hotel.getCheckOutCal().getTimeInMillis()) / 1000 / 60 / 60 / 24);
        if (day > 0)
            for(int i = 0; i <= day; i++) {
                TextView emptyText = new TextView(this);
                emptyText.setText("");
                emptyText.setBackground(getResources().getDrawable(R.drawable.border));
                emptyText.setTextSize(15);
                lParams = new TableRow.LayoutParams(column);
                tableRow.addView(emptyText, lParams);
                column++;
            }

        tLayout.addView(tableRow, tParams);
    }
}



















