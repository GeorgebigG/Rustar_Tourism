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
    private int id;

    public DatePickerForFragments(int id, int Year, int Month, int Day) {
        this.Year = Year;
        this.Month = Month;
        this.Day = Day;

        this.id = id;
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

            if (id == FirstPage.checkInDialogId) {
                long time = FirstPage.CHECK_IN_CAL.getTimeInMillis();
                FirstPage.CHECK_IN_CAL.set(Year, Month, Day);

                int nights = (int) ((FirstPage.CHECK_IN_CAL.getTimeInMillis() - time) / 1000 / 60 / 60 / 24);
                FirstPage.CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, nights);

                String checkOut = formater.format(FirstPage.CHECK_OUT_CAL.getTime());
                String checkIn = formater.format(FirstPage.CHECK_IN_CAL.getTime());

                FirstPage.checkOut.setText(checkOut);
                FirstPage.checkIn.setText(checkIn);

                Search.checkOut.setText(checkOut);
                Search.checkIn.setText(checkIn);

            } else {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Year, Month, Day);

                int nights = (int) ((calendar.getTimeInMillis() - FirstPage.CHECK_IN_CAL.getTimeInMillis()) / 1000 / 60 / 60 / 24);

                FirstPage.nights.setText("" + nights);
                Search.nights.setText("" + nights);
            }
        }
    };
}