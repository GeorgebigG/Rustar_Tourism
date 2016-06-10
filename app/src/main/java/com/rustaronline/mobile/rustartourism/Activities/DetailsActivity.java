package com.rustaronline.mobile.rustartourism.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Hotel;
import com.rustaronline.mobile.rustartourism.R;

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
        TableRow tableRow;
    }
}
