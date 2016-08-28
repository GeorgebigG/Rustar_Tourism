package com.rustaronline.mobile.rustartourism.Searches;

import android.os.AsyncTask;
import android.widget.Spinner;

import com.rustaronline.mobile.rustartourism.Activities.Search;
import com.rustaronline.mobile.rustartourism.Hotels.Hotel;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by georgenebieridze on 8/27/16.
 */
public class HotelPricesSearcher extends AsyncTask<String, Void, JSONObject> {

    Hotel hotel;
    Search search;

    String checkIn, checkOut;
    int nights, adults;

    ArrayList<Spinner> childrensAgeSpinner;
    int dailyFrom, dailyTo, totalFrom, totalTo;

    String meal;
    Calendar checkInCal;
    Calendar checkOutCal;

    public HotelPricesSearcher(Search search, Hotel hotel, String checkIn, int nights, String checkOut, int adults, ArrayList<Spinner> childrensAgeSpinner, int dailyFrom, int dailyTo, int totalFrom, int totalTo, String meal, Calendar checkInCal, Calendar checkOutCal) {
        this.search = search;
        this.hotel = hotel;

        this.checkIn = checkIn;
        this.nights = nights;
        this.checkOut = checkOut;

        this.adults = adults;
        this.childrensAgeSpinner = childrensAgeSpinner;

        this.dailyFrom = dailyFrom;
        this.dailyTo = dailyTo;

        this.totalFrom = totalFrom;
        this.totalTo = totalTo;

        this.meal = meal;

        this.checkInCal = checkInCal;
        this.checkOutCal = checkOutCal;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        HttpsURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(StaticClass.WebServiceUrl + StaticClass.HotelPriceName);
            connection = (HttpsURLConnection) url.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String code = "";

            String line = "";
            while ((line = reader.readLine()) != null)
                code += line;

            return new JSONObject(code);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();

            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return null;
    }


    @Override
    protected void onPostExecute(JSONObject object) {
        super.onPostExecute(object);

        try {
            JSONArray array = object.getJSONArray(JsonNames.contractPrices);

            JSONObject roomData;

            for (int i = 0; i < array.length(); i++) {
                roomData = array.getJSONObject(i);

                if (roomData == null)
                    continue;


                if (!roomData.getBoolean(JsonNames.isActive))
                    continue;


                String date = roomData.getString(JsonNames.startDate).split(JsonNames.dateAtimeSeparator)[0];
                final Calendar startDate = Calendar.getInstance();
                startDate.set(Search.tryParse(date.split(JsonNames.dateSeparator)[0]), Search.tryParse(date.split(JsonNames.dateSeparator)[1]), Search.tryParse(date.split(JsonNames.dateSeparator)[2]));

                date = roomData.getString(JsonNames.endDate).split(JsonNames.dateAtimeSeparator)[0];
                final Calendar endDate = Calendar.getInstance();
                endDate.set(Search.tryParse(date.split(JsonNames.dateSeparator)[0]), Search.tryParse(date.split(JsonNames.dateSeparator)[1]), Search.tryParse(date.split(JsonNames.dateSeparator)[2]));

                if (startDate.getTimeInMillis() > checkInCal.getTimeInMillis() || endDate.getTimeInMillis() < checkOutCal.getTimeInMillis())
                    continue;


                if (!meal.equals(search.getActivity().getResources().getStringArray(R.array.meals)[0]))
                    if (!meal.equals(roomData.getString(JsonNames.mealPlan))) continue;


                if (Search.tryParse(roomData.getString(JsonNames.adults)) < adults)
                    continue;


                int teens = 0, childrens = 0, infants = 0;
                JSONObject childPolicy = roomData.getJSONObject(JsonNames.childrenPolicy);

                for (Spinner children : childrensAgeSpinner) {
                    if (doubleTryParse(children.getSelectedItem().toString()) <= childPolicy.getDouble(JsonNames.infantsMaxAge))
                        infants++;

                    else if (doubleTryParse(children.getSelectedItem().toString()) <= childPolicy.getDouble(JsonNames.childrenMaxAge))
                        childrens++;

                    else teens++;
                }

                if ((roomData.getInt(JsonNames.teens) + roomData.getInt(JsonNames.teensWithExtraBeds)) < teens)
                    continue;


                if ((roomData.getInt(JsonNames.children) + roomData.getInt(JsonNames.childrenWithExtraBeds)) < childrens)
                    continue;


                if (roomData.getInt(JsonNames.infants) < infants)
                    continue;


                if (dailyFrom != 0 && dailyFrom < roomData.getInt(JsonNames.price))
                    continue;

                if (dailyTo != 0 && dailyTo < roomData.getInt(JsonNames.price))
                    continue;

                if (totalFrom != 0 && totalFrom < (roomData.getInt(JsonNames.price) * nights))
                    continue;

                if (totalTo != 0 && totalTo < (roomData.getInt(JsonNames.price) * nights))
                    continue;


                hotel.addRoom(hotel.getHotelid(), roomData.getString(JsonNames.roomCategoryName), meal, checkInCal.getTime(), checkOutCal.getTime(), roomData.getInt(JsonNames.price), roomData.getString(JsonNames.currency));
            }

            if (hotel.getRooms().size() == 0)
                return;


            search.addHotel(hotel);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    double doubleTryParse(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception ex) {
            return  0.0f;
        }
    }
}



/*

{
    "priceid": "aa55f001-42d5-4f8c-87fd-a5284d766c49",
    "hotelid": "d6f9299f-5d94-4955-85e7-003e0383023c",
    "roomcategory": "c6b766a9-fbbd-4fed-b9cb-b0a941957319",
    "roomcategorytype": "73c367d3-d8e1-4c0d-aa6d-9f567657133b",
    "roomcategoryname": "Standard Room",
    "startdate": "2016-05-16T00:00:00",
    "enddate": "2016-09-30T00:00:00",
    "mealplan": "BB",
    "adults": 1,
    "teens": 0,
    "children": 0,
    "infants": 0,
    "teenswithextrabeds": 0,
    "childrenwithextrabeds": 0,
    "childpolicy": {
        "infantmaxage": 1.99,
        "childmaxage": 9.99,
        "teenmaxage": 15.99
    },
    "placement": "SGL",
    "price": 48,
    "currency": "USD",
    "promotioncode": "",
    "promotion": {
        "staydays": null,
        "paydays": null,
        "discount": 0,
        "cashback": 0
    },
    "conditions": {
        "bookingstartdate": null,
        "bookingenddate": null,
        "bookbefore": null,
        "minimumstay": 0
    },
    "isactive": true
}

 */

















