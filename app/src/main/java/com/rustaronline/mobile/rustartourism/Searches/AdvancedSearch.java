package com.rustaronline.mobile.rustartourism.Searches;

import android.widget.Spinner;

import com.rustaronline.mobile.rustartourism.Hotel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by georgenebieridze on 8/21/16.
 */
public class AdvancedSearch {

    public static Hotel advancedSearchReslust(String checkIn, int amountOfNight, String checkOut, String hotelname, String city, String district, int amountOfAdults, ArrayList<Spinner> childrensAgeSpinner, int dailyFrom, int dailyTo, int totalFrom, int totalTo, String type, String meal, boolean fiveStar, boolean fourStar, boolean threeStar, boolean twoStar, boolean oneStar, boolean apartment, boolean alcohol, boolean freeWifi, boolean pool, boolean metro, boolean mall, Calendar checkInCal, Calendar checkOutCal) {
//        int minPrice = dailyFrom == 0 ? totalFrom == 0 ? 0 : (totalFrom/amountOfNight) : dailyFrom;
//        int maxPrice = dailyTo == 0 ? totalTo == 0 ? 0 : (totalTo/amountOfNight) : dailyTo;
//        boolean[] stars = new boolean[] { oneStar, twoStar, threeStar, fourStar, fiveStar };
//
//        if (hotelname.equals(""))
//            hotelname = "Rixsos";
//        Hotel findHotel = new Hotel(firstHotel, secondHotel, maxPrice, minPrice, checkIn, checkOut, hotelname, stars, amountOfNight, checkInCal, checkOutCal, meal);



        return null;
    }
}
