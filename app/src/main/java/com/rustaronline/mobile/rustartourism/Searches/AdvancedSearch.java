package com.rustaronline.mobile.rustartourism.Searches;

import android.content.Context;
import android.widget.Spinner;

import com.rustaronline.mobile.rustartourism.Activities.Search;
import com.rustaronline.mobile.rustartourism.Hotels.Hotel;
import com.rustaronline.mobile.rustartourism.StaticClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by georgenebieridze on 8/21/16.
 */
public class AdvancedSearch {

    private static int index = 0;

    public static void advancedSearchResult(Search search, String checkIn, int amountOfNight, String checkOut, String hotelname, String city, String district, int amountOfAdults,
                                            ArrayList<Spinner> childrensAgeSpinner, int dailyFrom, int dailyTo, int totalFrom, int totalTo, String type, String meal,
                                            boolean fiveStar, boolean fourStar, boolean threeStar, boolean twoStar, boolean oneStar, boolean apartment, boolean alcohol,
                                            boolean freeWifi, boolean pool, boolean metro, boolean mall, Calendar checkInCal, Calendar checkOutCal) {

        try {

            JSONObject hotelData;

            int addedRoom = 0;

            JSONArray array = StaticClass.rustarWebService.getJSONArray(JsonNames.dataName);

            for (int i = index; i < array.length(); i++, index++) {

                hotelData = array.getJSONObject(i);

                if (!hotelData.getString(JsonNames.hotelName).toLowerCase().contains(hotelname.toLowerCase()))
                    continue;


                if (!hotelData.getString(JsonNames.priceStatus).equals(JsonNames.priceReady))
                    continue;


                if (!hotelData.getString(JsonNames.cityName).toLowerCase().contains(city.toLowerCase()))
                    continue;


                if (!hotelData.getString(JsonNames.distinctName).toLowerCase().contains(district.toLowerCase()))
                    continue;


                if (!hotelData.getBoolean(JsonNames.alcohol) && alcohol)
                    continue;


                if (!hotelData.getBoolean(JsonNames.freeWifi) && freeWifi)
                    continue;


                if (!hotelData.getBoolean(JsonNames.mall) && mall)
                    continue;


                if (!hotelData.getBoolean(JsonNames.metro) && metro)
                    continue;


                if (!hotelData.getBoolean(JsonNames.pool) && pool)
                    continue;


                if (!hotelData.getString(JsonNames.hotelClass).equals(JsonNames.apartments)) {

                    if (apartment)
                        continue;

                    if (fiveStar || fourStar || threeStar || twoStar || oneStar) {

                        switch (hotelData.getString(JsonNames.hotelClass).length()) {
                            case 5:
                                if (!fiveStar)
                                    continue;
                                break;

                            case 4:
                                if (!fourStar)
                                    continue;
                                break;

                            case 3:
                                if (!threeStar)
                                    continue;
                                break;

                            case 2:
                                if (!twoStar)
                                    continue;
                                break;

                            case 1:
                                if (!oneStar)
                                    continue;
                                break;
                        }
                    }
                }

                if (type.equals(JsonNames.typeCity) || type.equals(JsonNames.typeBeach)) {
                    if (hotelData.getString(JsonNames.hotelType).equals(JsonNames.typeCity)) {
                        if (!type.equals(JsonNames.typeCity))
                            continue;
                    }

                    else {
                        if (!type.equals(JsonNames.typeBeach))
                            continue;
                    }
                }

                Hotel hotel = new Hotel(hotelData.getString(JsonNames.hotelid), hotelData.getString(JsonNames.imageUrl), hotelData.getString(JsonNames.hotelName),
                        (hotelData.getString(JsonNames.hotelClass).equals(JsonNames.apartments)) ? Hotel.APARTMENT : hotelData.getString(JsonNames.hotelClass).length(),
                        amountOfNight);

                new HotelPricesSearcher(search, hotel, checkIn, amountOfNight, checkOut, amountOfAdults, childrensAgeSpinner, dailyFrom, dailyTo, totalFrom, totalTo, meal, checkInCal, checkOutCal);

                if (++addedRoom >= 10) return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}