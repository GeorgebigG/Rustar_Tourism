package com.rustaronline.mobile.rustartourism.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.rustaronline.mobile.rustartourism.Helper.AnimationClass;
import com.rustaronline.mobile.rustartourism.Helper.DatePickerForFragments;
import com.rustaronline.mobile.rustartourism.Helper.downloadImageFromUrl;
import com.rustaronline.mobile.rustartourism.Hotel;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.Searches.AdvancedSearch;
import com.rustaronline.mobile.rustartourism.StaticClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    ArrayList<Spinner> childrensAgeSpinners;

    private static LinearLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_search, container, false);

        setSearchParameters();

        childrensAgeSpinners = new ArrayList<Spinner>();

        checkOut.setOnClickListener(this);
        checkIn.setOnClickListener(this);
        AdvSearch.setOnClickListener(this);
        AnimationClass.setAnimation(AdvSearch, getResources().getColor(R.color.rustarGreen), getResources().getColor(R.color.clickColour));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        checkIn.setText(dateFormat.format(FirstPage.CHECK_IN_CAL.getTime()));
        checkOut.setText(dateFormat.format(FirstPage.CHECK_OUT_CAL.getTime()));

        amountOfChildrenSpinner.setOnItemSelectedListener(this);

        nights.addTextChangedListener(FirstPage.watcher);

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
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onClick(View v) {
        DatePickerForFragments date;
        switch (v.getId()) {
            case R.id.ScheckIn:
                date = new DatePickerForFragments(FirstPage.checkInDialogId, FirstPage.CHECK_IN_CAL.get(Calendar.YEAR), FirstPage.CHECK_IN_CAL.get(Calendar.MONTH), FirstPage.CHECK_IN_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.ScheckOut:
                date = new DatePickerForFragments(FirstPage.checkOutDialogId, FirstPage.CHECK_OUT_CAL.get(Calendar.YEAR), FirstPage.CHECK_OUT_CAL.get(Calendar.MONTH), FirstPage.CHECK_OUT_CAL.get(Calendar.DAY_OF_MONTH));
                date.show(getFragmentManager().beginTransaction(), "Date");
                break;
            case R.id.AdvSearch:
                Hotel[] searchHotels = new Hotel[Hotel.getAmountOfHotels()];

                for (int i = 0; i < searchHotels.length; i++)
                    searchHotels[i] = AdvancedSearch.advancedSearchReslust(checkIn.getText().toString(),
                        Integer.parseInt(nights.getText().toString()), checkOut.getText().toString(),
                        hotel.getText().toString(), city.getText().toString(), district.getText().toString(),
                        Integer.parseInt(amountOfAdults.getSelectedItem().toString()), childrensAgeSpinners, tryParse(dailyFrom.getText().toString()),
                        tryParse(dailyTo.getText().toString()), tryParse(totalFrom.getText().toString()),tryParse(totalTo.getText().toString()),
                        type.getSelectedItem().toString(), meal.getSelectedItem().toString(), fiveS.isChecked(),
                        fourS.isChecked(), threeS.isChecked(), twoS.isChecked(), oneS.isChecked(), apartment.isChecked(),
                        alcohol.isChecked(), freeWifi.isChecked(), pool.isChecked(), metro.isChecked(), mall.isChecked(),
                            (Calendar) FirstPage.CHECK_IN_CAL.clone(), (Calendar) FirstPage.CHECK_OUT_CAL.clone());

                createHotelChoices(searchHotels);
                break;
            default:
                break;
        }
    }

    private void createHotelChoices(Hotel[] searchHotels) {
        mainLayout.removeAllViews();

        FrameLayout fLayout;
        FrameLayout.LayoutParams fParams;
        LinearLayout.LayoutParams lParams;
        RatingBar ratingBar;
        TextView textView;

        for (final Hotel hotel : searchHotels) {
            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final ImageView image = new ImageView(getActivity());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setClickable(true);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    DetailsActivity.hotel = hotel;
                    startActivity(intent);
                }
            });
            new downloadImageFromUrl(image, hotel.getImageURL()).execute();
            mainLayout.addView(image, lParams);

            fLayout = new FrameLayout(getActivity());
            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.bottomMargin = 10;
            fLayout.setLayoutParams(lParams);

            fParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ratingBar = new RatingBar(getActivity(), null, attr.ratingBarStyleSmall);
            ratingBar.setRating(hotel.getStar());
            fParams.gravity = Gravity.LEFT;
            fParams.topMargin = 20;
            fLayout.addView(ratingBar, fParams);

            fParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView priceText = new TextView(getActivity());
            priceText.setTextColor(getResources().getColor(R.color.lightRustarGreen));
            priceText.setText(getResources().getString(R.string.Dollar) + (hotel.getPrice() * hotel.getAmountOfNights()));
            priceText.setTextSize(30);
            fParams.gravity =  Gravity.RIGHT;
            fLayout.addView(priceText, fParams);

            mainLayout.addView(fLayout);

            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView = new TextView(getActivity());
            textView.setTextColor(getResources().getColor(R.color.black));
            lParams.gravity = Gravity.CENTER;
            lParams.bottomMargin = 10;

            textView.setText(getResources().getString(R.string.HotelName) + " " + hotel.getName() +
                    "\n" + getResources().getString(R.string.PricePerNight) + " "  + getResources().getString(R.string.Dollar) + hotel.getPrice() +
                    ".\n" + getResources().getString(R.string.meal) + " " + hotel.getMeal() +
                    ".\n" + getResources().getString(R.string.CheckInDate) + " " + hotel.getCheckInDate() +
                    ".\n" + getResources().getString(R.string.CheckOutDate) + " " + hotel.getCheckOutDate() + ".");
            mainLayout.addView(textView, lParams);

            lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TextView details = new TextView(getActivity());
            details.setText(getResources().getText(R.string.Details));
            details.setTextColor(getResources().getColor(R.color.lightRustarGreen));
            details.setClickable(true);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image.callOnClick();
                }
            });
            lParams.gravity = Gravity.CENTER;
            lParams.bottomMargin = 10;
            mainLayout.addView(details, lParams);
        }
    }

    public static int tryParse(String number) {
        try {
            int g = Integer.parseInt(number);
            return g;
        } catch (Exception ex) {
            return 0;
        }
    }
}










