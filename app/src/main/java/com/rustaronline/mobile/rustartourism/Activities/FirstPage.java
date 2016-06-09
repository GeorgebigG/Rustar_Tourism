package com.rustaronline.mobile.rustartourism.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Helper.DatePickerForFragments;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.StaticClass;
import com.rustaronline.mobile.rustartourism.Helper.downloadImageFromUrl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gio on 12/05/16.
 */
public class FirstPage extends Fragment implements View.OnClickListener {

    public static EditText checkIn, checkOut;
    public static EditText nights, location, holtel, pax;

    public static final int checkInDialogId = 0, checkOutDialogId = 1;

    public static Button search;
    public static TabLayout tabLayout;
    public static ImageView imageView;
    TextView dailuFrom;

    private String firstImageUrl, secondImageUrl, therdImageUrl, fourthImageUrl, fifthImageUrl;

    public static final Calendar CHECK_IN_CAL = Calendar.getInstance(), CHECK_OUT_CAL = Calendar.getInstance();

    View context;
    View v;

    int amountOfDay = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_firstpage, container, false);
        context = inflater.inflate(R.layout.activity_firstpage, container, false);

        checkIn = (EditText) v.findViewById(R.id.checkIn);
        checkIn.setOnClickListener(this);
        checkOut = (EditText) v.findViewById(R.id.checkOut);
        checkOut.setOnClickListener(this);

        CHECK_IN_CAL.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = CHECK_IN_CAL.getTime();

        CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, 2);
        Date checkOutDate = CHECK_OUT_CAL.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        String checkOutAsString = dateFormat.format(checkOutDate);

        checkIn.setText(tomorrowAsString);
        checkOut.setText(checkOutAsString);

        search = (Button) v.findViewById(R.id.Search);
        search.setOnClickListener(this);

        location = (EditText) v.findViewById(R.id.location);
        nights = (EditText) v.findViewById(R.id.nights);

        holtel = (EditText) v.findViewById(R.id.hotel);
        pax = (EditText) v.findViewById(R.id.pax);

        dailuFrom = (TextView) v.findViewById(R.id.dailyFrom);

        setPictureToImageView(v);

        nights.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!nights.getText().toString().equals("")) {
                    int day = Integer.parseInt(nights.getText().toString()) - amountOfDay;
                    CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, day);
                    checkOut.setText(new SimpleDateFormat("dd.MM.yyyy").format(CHECK_OUT_CAL.getTime()));
                    amountOfDay = Integer.parseInt(nights.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        DatePickerForFragments date;

        switch (v.getId()) {
            case R.id.checkIn:
                date = new DatePickerForFragments(checkInDialogId, 0, CHECK_IN_CAL.get(Calendar.YEAR), CHECK_IN_CAL.get(Calendar.MONTH), CHECK_IN_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.checkOut:
                date = new DatePickerForFragments(checkOutDialogId, 0, CHECK_OUT_CAL.get(Calendar.YEAR), CHECK_OUT_CAL.get(Calendar.MONTH), CHECK_OUT_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.Search:
                StaticClass.searchRelust(checkIn.getText().toString(), Integer.parseInt(nights.getText().toString()),
                        checkOut.getText().toString(), location.getText().toString(), holtel.getText().toString(),
                        pax.getText().toString());
                tabLayout.getTabAt(1).select();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LoginActivity.pd.cancel();
    }

    private void setPictureToImageView(View v) {
        firstImageUrl =  "http://images.asiatravel.com/Hotel/11480/11480logo.png";
        secondImageUrl = "http://images.asiatravel.com/Hotel/11480/11480logo.png";
        therdImageUrl =  "http://images.asiatravel.com/Hotel/11480/11480logo.png";
        fourthImageUrl = "http://images.asiatravel.com/Hotel/11480/11480logo.png";
        fifthImageUrl =  "http://images.asiatravel.com/Hotel/11480/11480logo.png";

        imageView = (ImageView) v.findViewById(R.id.firstImage);
        new downloadImageFromUrl(imageView, firstImageUrl).execute();
        imageView = (ImageView) v.findViewById(R.id.secondImage);
        new downloadImageFromUrl(imageView, secondImageUrl).execute();
        imageView = (ImageView) v.findViewById(R.id.thirdImage);
        new downloadImageFromUrl(imageView, therdImageUrl).execute();
        imageView = (ImageView) v.findViewById(R.id.fourthImage);
        new downloadImageFromUrl(imageView, fourthImageUrl).execute();
        imageView = (ImageView) v.findViewById(R.id.fifthImage);
        new downloadImageFromUrl(imageView, fifthImageUrl).execute();
    }
}
