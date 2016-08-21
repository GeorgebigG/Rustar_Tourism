package com.rustaronline.mobile.rustartourism.Activities;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Helper.AnimationClass;
import com.rustaronline.mobile.rustartourism.Helper.DatePickerForFragments;
import com.rustaronline.mobile.rustartourism.Helper.downloadImageFromUrl;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.StaticClass;

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

    public static TextWatcher watcher;

    public static final Calendar CHECK_IN_CAL = Calendar.getInstance(), CHECK_OUT_CAL = Calendar.getInstance();

    View context;
    View v;

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
        AnimationClass.setAnimation(search, getResources().getColor(R.color.rustarGreen), getResources().getColor(R.color.clickColour));

        location = (EditText) v.findViewById(R.id.location);
        nights = (EditText) v.findViewById(R.id.nights);

        holtel = (EditText) v.findViewById(R.id.hotel);
        pax = (EditText) v.findViewById(R.id.pax);

        dailuFrom = (TextView) v.findViewById(R.id.dailyFrom);

        setPictureToImageView(v);

        createTextWatcher();

        nights.addTextChangedListener(watcher);

        return v;
    }


    public void createTextWatcher() {
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(CHECK_IN_CAL.get(Calendar.YEAR), CHECK_IN_CAL.get(Calendar.MONTH), CHECK_IN_CAL.get(Calendar.DAY_OF_MONTH));

                int day = Search.tryParse(nights.getText().toString());
                calendar.add(Calendar.DAY_OF_YEAR, day);

                CHECK_OUT_CAL.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                checkOut.setText(new SimpleDateFormat("dd.MM.yyyy").format(CHECK_OUT_CAL.getTime()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
    }


    @Override
    public void onClick(View v) {
        DatePickerForFragments date;

        switch (v.getId()) {
            case R.id.checkIn:
                date = new DatePickerForFragments(checkInDialogId, CHECK_IN_CAL.get(Calendar.YEAR), CHECK_IN_CAL.get(Calendar.MONTH), CHECK_IN_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.checkOut:
                date = new DatePickerForFragments(checkOutDialogId, CHECK_OUT_CAL.get(Calendar.YEAR), CHECK_OUT_CAL.get(Calendar.MONTH), CHECK_OUT_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.Search:
                if (nights.getText().toString().length() == 0) nights.setText("1");

                StaticClass.searchReslust(checkIn.getText().toString(), Integer.parseInt(nights.getText().toString()),
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
    }

    private void setPictureToImageView(View v) {
        String url  =  "https://www.rustaronline.com/images/logo.png";

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.bestHotelAdvices);
        LinearLayout.LayoutParams lParams;
        for (int i = 0; i < 5; i++) {
            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            new downloadImageFromUrl(imageView, url);
            layout.addView(imageView, lParams);
        }
    }
}
