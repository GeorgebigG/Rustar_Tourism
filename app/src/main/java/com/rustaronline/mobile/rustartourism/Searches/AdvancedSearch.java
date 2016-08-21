package com.rustaronline.mobile.rustartourism.Searches;

import android.widget.Spinner;

import com.rustaronline.mobile.rustartourism.Activities.FirstPage;
import com.rustaronline.mobile.rustartourism.Hotel;
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

    public static Hotel advancedSearchReslust(String checkIn, int amountOfNight, String checkOut, String hotelname, String city, String district, int amountOfAdults,
                                              ArrayList<Spinner> childrensAgeSpinner, int dailyFrom, int dailyTo, int totalFrom, int totalTo, String type, String meal,
                                              boolean fiveStar, boolean fourStar, boolean threeStar, boolean twoStar, boolean oneStar, boolean apartment, boolean alcohol,
                                              boolean freeWifi, boolean pool, boolean metro, boolean mall, Calendar checkInCal, Calendar checkOutCal) {

        try {
            JSONObject hotelData;

            JSONArray array = StaticClass.rustarWebService.getJSONArray(JsonNames.rustarDataName);

            for (int i = index; i < array.length(); i++) {

                hotelData = array.getJSONObject(i);


                if (!hotelData.getString(JsonNames.rustarHotelName).toLowerCase().contains(hotelname.toLowerCase()))
                    continue;


                if (!hotelData.getString(JsonNames.rustarPriceStatus).equals(JsonNames.rustarPriceReady))
                    continue;


                if (!hotelData.getString(JsonNames.rustarCityName).toLowerCase().equals(city.toLowerCase()))
                    continue;


                if (!hotelData.getString(JsonNames.rustarDistinctName).toLowerCase().contains(district.toLowerCase()))
                    continue;


                if (hotelData.getBoolean(JsonNames.rustarAlcohol) && !alcohol)
                    continue;


                if (hotelData.getBoolean(JsonNames.rustarFreeWifi) && !freeWifi)
                    continue;


                if (hotelData.getBoolean(JsonNames.rustarMall) && !mall)
                    continue;


                if (hotelData.getBoolean(JsonNames.rustarMetro) && !metro)
                    continue;


                if (hotelData.getBoolean(JsonNames.rustarPool) && !pool)
                    continue;


                if (fiveStar || fourStar || threeStar || twoStar || oneStar) {

                    switch (hotelData.getString(JsonNames.rustarHotelClass).length()) {
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

                if (type.equals(JsonNames.rustarTypeCity) || type.equals(JsonNames.rustarTypeBeach)) {
                    if (hotelData.getString(JsonNames.rustarHotelType).equals(JsonNames.rustarTypeCity)) {
                        if (!type.equals(JsonNames.rustarTypeCity))
                            continue;
                    }

                    else {
                        if (!type.equals(JsonNames.rustarTypeBeach))
                            continue;
                    }
                }

                index = i;

                return new Hotel(hotelData.getString(JsonNames.rustarImageUrl), dailyTo, checkIn, checkOut, hotelData.getString(JsonNames.rustarHotelName), hotelData.getString(JsonNames.rustarHotelClass).length(),
                        amountOfNight, FirstPage.CHECK_IN_CAL, FirstPage.CHECK_OUT_CAL, "Any");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}











