package com.rustaronline.mobile.rustartourism.Helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Activities.FirstPage;
import com.rustaronline.mobile.rustartourism.Activities.Search;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gio on 5/25/16.
 */
public class DatePickerForFragments extends DialogFragment {
    private int Year, Month, Day;

    private String date;

    private int Class;
    private int id;

    public DatePickerForFragments(int id, int Class, int Year, int Month, int Day) {
        this.Year = Year;
        this.Month = Month;
        this.Day = Day;

        this.id = id;
        this.Class = Class;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, Year, Month, Day);

        return dialog;
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");

            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            if (Class == 0) {
                if (id == FirstPage.checkInDialogId) {
                    long time = FirstPage.CHECK_IN_CAL.getTimeInMillis();
                    FirstPage.CHECK_IN_CAL.set(Year, Month, Day);
                    int nights = (int) ((FirstPage.CHECK_IN_CAL.getTimeInMillis() - time) / 1000 / 60 / 60 / 24);
                    FirstPage.CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, nights);
                    FirstPage.checkOut.setText(formater.format(FirstPage.CHECK_OUT_CAL.getTime()));
                    FirstPage.checkIn.setText(formater.format(FirstPage.CHECK_IN_CAL.getTime()));
                } else {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(Year, Month, Day);
                    int nights = (int) ((calendar.getTimeInMillis() - FirstPage.CHECK_OUT_CAL.getTimeInMillis()) / 1000 / 60 / 60 / 24);
                    int amountOfNights = Integer.parseInt(FirstPage.nights.getText().toString()) + nights;
                    FirstPage.nights.setText("" + amountOfNights);
                }
            } else {
                if (id == FirstPage.checkInDialogId) {
                    long time = Search.CHECK_IN_CAL.getTimeInMillis();
                    Search.CHECK_IN_CAL.set(Year, Month, Day);
                    int nights = (int) ((Search.CHECK_IN_CAL.getTimeInMillis() - time) / 1000 / 60 / 60 / 24);
                    Search.CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, nights);
                    Search.checkOut.setText(formater.format(Search.CHECK_OUT_CAL.getTime()));
                    Search.checkIn.setText(formater.format(Search.CHECK_IN_CAL.getTime()));
                } else {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(Year, Month, Day);
                    int nights = (int) ((calendar.getTimeInMillis() - Search.CHECK_OUT_CAL.getTimeInMillis()) / 1000 / 60 / 60 / 24);
                    int amountOfNights = Integer.parseInt(Search.nights.getText().toString()) + nights;
                    Search.nights.setText("" + amountOfNights);
                }
            }
        }
    };
}








