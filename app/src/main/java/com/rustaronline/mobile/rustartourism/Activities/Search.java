package com.rustaronline.mobile.rustartourism.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Helper.DatePickerForFragments;
import com.rustaronline.mobile.rustartourism.Helper.downloadImageFromUrl;
import com.rustaronline.mobile.rustartourism.Hotel;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.StaticClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.*;

/**
 * Created by gio on 14/05/16.
 */
public class Search extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static View v;
    public static EditText checkIn, checkOut, nights, hotel, city, district, dailyFrom, dailyTo, totalFrom, totalTo;
    private static Spinner amountOfAdults, amountOfChildrenSpinner, type, meal;
    private static TextView ageText;

    private static Button AdvSearch;
    private static FrameLayout firstThree, anotherThree;

    private static CheckBox fiveS, fourS, threeS, twoS, oneS, apartment, alcohol, freeWifi, pool, metro, mall;
    int amountOfDay = 1;

    public static final Calendar CHECK_IN_CAL = Calendar.getInstance(), CHECK_OUT_CAL = Calendar.getInstance();

    ArrayList<Spinner> childrensAgeSpinners;

    private static LinearLayout mainLayout;

    public static ArrayList<ImageView> allImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_search, container, false);

        setSearchParameters();

        childrensAgeSpinners = new ArrayList<Spinner>();

        checkOut.setOnClickListener(this);
        checkIn.setOnClickListener(this);
        AdvSearch.setOnClickListener(this);

        CHECK_IN_CAL.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = CHECK_IN_CAL.getTime();


        CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, 2);
        Date afterTomorrow = CHECK_OUT_CAL.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        String afterTomorrowAsString = dateFormat.format(afterTomorrow);

        checkIn.setText(tomorrowAsString);
        checkOut.setText(afterTomorrowAsString);

        amountOfChildrenSpinner.setOnItemSelectedListener(this);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    int day = Integer.parseInt(nights.getText().toString()) - amountOfDay;
                    CHECK_OUT_CAL.add(Calendar.DAY_OF_YEAR, day);
                    checkOut.setText(new SimpleDateFormat("dd.MM.yyyy").format(CHECK_OUT_CAL.getTime()));
                    amountOfDay = Integer.parseInt(nights.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        nights.addTextChangedListener(watcher);

        return v;
    }

    private static void setSearchParameters() {
        mainLayout = (LinearLayout) v.findViewById(R.id.addNewViews);

        firstThree = (FrameLayout) v.findViewById(R.id.firstThree);
        anotherThree = (FrameLayout) v.findViewById(R.id.anotherThree);


        checkIn = (EditText) v.findViewById(R.id.ScheckIn);
        nights = (EditText) v.findViewById(R.id.Snights);
        checkOut = (EditText) v.findViewById(R.id.ScheckOut);
        AdvSearch = (Button) v.findViewById(R.id.AdvSearch);

        hotel = (EditText) v.findViewById(R.id.hotelName);
        city = (EditText) v.findViewById(R.id.cityName);
        district = (EditText) v.findViewById(R.id.districtName);
        dailyFrom = (EditText) v.findViewById(R.id.dailyFrom);
        dailyTo = (EditText) v.findViewById(R.id.dailyTo);
        totalFrom = (EditText) v.findViewById(R.id.totalFrom);
        totalTo = (EditText) v.findViewById(R.id.totalTo);

        amountOfChildrenSpinner = (Spinner) v.findViewById(R.id.childrenSpinner);
        amountOfAdults = (Spinner) v.findViewById(R.id.adultSpinner);
        type = (Spinner) v.findViewById(R.id.typeSpinner);
        meal = (Spinner) v.findViewById(R.id.mealSpinner);

        fiveS = (CheckBox) v.findViewById(R.id.fiveS);
        fourS = (CheckBox) v.findViewById(R.id.fourS);
        threeS = (CheckBox) v.findViewById(R.id.threeS);
        twoS = (CheckBox) v.findViewById(R.id.twoS);
        oneS = (CheckBox) v.findViewById(R.id.oneS);

        apartment = (CheckBox) v.findViewById(R.id.includeApartment);
        alcohol = (CheckBox) v.findViewById(R.id.includeAlcohol);
        freeWifi = (CheckBox) v.findViewById(R.id.includeFreeWIFI);
        pool = (CheckBox) v.findViewById(R.id.includePool);
        metro = (CheckBox) v.findViewById(R.id.includeMetro);
        mall = (CheckBox) v.findViewById(R.id.includeMall);

        ageText = (TextView) v.findViewById(R.id.isChildrenOrNo);

        allImages = new ArrayList<ImageView>();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        firstThree.removeAllViews();
        anotherThree.removeAllViews();
        ageText.setText("");
        childrensAgeSpinners.clear();
        int amount = Integer.parseInt(amountOfChildrenSpinner.getSelectedItem().toString());
        int count = 0;
        if (amount > 0)
            ageText.setText(R.string.ageText);


        Spinner spinner;
        FrameLayout.LayoutParams lParams;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ages, layout.simple_spinner_dropdown_item);
        for (int i = 0; i < amount; i++) {
            lParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            spinner = new Spinner(getActivity());
            spinner.setAdapter(adapter);
            count++;


            if (count == 1 || count == 4)
                lParams.gravity = Gravity.LEFT;
            else if (count == 2 || count == 5)
                lParams.gravity = Gravity.CENTER;
            else if (count == 3 || count == 6)
                lParams.gravity = Gravity.RIGHT;


            if (count <= 3)
                firstThree.addView(spinner, lParams);
            else
                anotherThree.addView(spinner, lParams);

            childrensAgeSpinners.add(spinner);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        DatePickerForFragments date;
        switch (v.getId()) {
            case R.id.ScheckIn:
                date = new DatePickerForFragments(FirstPage.checkInDialogId, 1, CHECK_IN_CAL.get(Calendar.YEAR), CHECK_IN_CAL.get(Calendar.MONTH), CHECK_IN_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.ScheckOut:
                date = new DatePickerForFragments(FirstPage.checkOutDialogId, 1, CHECK_OUT_CAL.get(Calendar.YEAR), CHECK_OUT_CAL.get(Calendar.MONTH), CHECK_OUT_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.AdvSearch:
                int daFrom, daTo, totFrom, totTo;

                if (nights.getText().toString().equals(""))
                    nights.setText("1");

                if (dailyFrom.getText().toString().equals(""))
                    daFrom = 0;
                else
                    daFrom = Integer.parseInt(dailyFrom.getText().toString());

                if (dailyTo.getText().toString().equals(""))
                    daTo = 0;
                else
                    daTo = Integer.parseInt(dailyTo.getText().toString());
                if (totalFrom.getText().toString().equals(""))
                    totFrom = 0;
                else
                    totFrom = Integer.parseInt(totalFrom.getText().toString());
                if (totalTo.getText().toString().equals(""))
                    totTo = 0;
                else
                    totTo = Integer.parseInt(totalTo.getText().toString());


                Hotel searchHotel = StaticClass.advancedSearchRelust(checkIn.getText().toString(),
                        Integer.parseInt(nights.getText().toString()), checkOut.getText().toString(),
                        hotel.getText().toString(), city.getText().toString(), district.getText().toString(),
                        Integer.parseInt(amountOfAdults.getSelectedItem().toString()), childrensAgeSpinners, daFrom, daTo,
                        totFrom,totTo, type.getSelectedItem().toString(), meal.getSelectedItem().toString(), fiveS.isChecked(),
                        fourS.isChecked(), threeS.isChecked(), twoS.isChecked(), oneS.isChecked(), apartment.isChecked(),
                        alcohol.isChecked(), freeWifi.isChecked(), pool.isChecked(), metro.isChecked(), mall.isChecked());

                createHotelChoices(searchHotel);
                break;
            default:
                break;
        }
    }

    private void createHotelChoices(Hotel searchHotel) {
        mainLayout.removeAllViews();

        LinearLayout.LayoutParams lParams;
        ImageView image;
        RatingBar ratingBar;
        TextView textView;

        for (int i = 0; i < searchHotel.getAmountOfHotels(); i++) {
            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            image = new ImageView(getActivity());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setClickable(true);
            allImages.add(image);
            new downloadImageFromUrl(image, searchHotel.getImageURL()).execute();
            mainLayout.addView(image, lParams);

            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ratingBar = new RatingBar(getActivity(), null, attr.ratingBarStyleSmall);
            ratingBar.setRating(searchHotel.getStar());
            lParams.gravity = Gravity.CENTER;
            lParams.bottomMargin = 10;
            mainLayout.addView(ratingBar, lParams);

            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView = new TextView(getActivity());
            textView.setTextColor(getResources().getColor(R.color.black));
            lParams.gravity = Gravity.CENTER;
            lParams.bottomMargin = 10;

            textView.setText(getResources().getString(R.string.HotelName) + " " + searchHotel.getName() +
                    "\n" + getResources().getString(R.string.PricePerNight) + " " + searchHotel.getPrice() + getResources().getString(R.string.Dollar) + ".\n" +
                    getResources().getString(R.string.TotalPrice) + " " + (searchHotel.getPrice() * Integer.parseInt(nights.getText().toString())) +
                    getResources().getString(R.string.Dollar) + ".\n" + getResources().getString(R.string.CheckInDate) + " " + searchHotel.getCheckInDate() +
                    ".\n" + getResources().getString(R.string.CheckOutDate) + " " + searchHotel.getCheckOutDate() + ".");
            mainLayout.addView(textView, lParams);

            searchHotel.changeHotel();
        }
    }
}










