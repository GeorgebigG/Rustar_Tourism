package com.rustaronline.mobile.rustartourism;

import android.os.AsyncTask;
import android.widget.Spinner;

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
 * Created by gio on 30/04/16.
 */
public class StaticClass {
    public static boolean accauntBlocked;
    public static String firstHotel = "https://www.rustaronline.com/images/pages/hotels/thumbnail/A9AF2DFE-73DC-441A-A972-CC6136534569.jpg";
    public static String secondHotel = "https://www.rustaronline.com/images/pages/hotels/thumbnail/78468066-FCA7-472C-AABD-2143DF1DC718.jpg";


    private static String WebServiceUrl = "https://restapi.rustaronline.com/v1.00/api/hotels?";
    private static String WebSAnd = "&";
    private static String WebSName = "agentid=";
    private static String WebSPassword = "agentpassword=";

    private static JSONObject rustarWebService = null;
    private static String codeVariableName = "Code";
    private static String correctCode = "00";

    public static String getString(String name, String password) {
        boolean correctPassword = checkPassword(name, password);

        if (correctPassword && !isAccauntBlocked(name, password))
            return "Correct";
        else if (!correctPassword)
            return "password or username wrong";
        else
            return "User Blocked!";
    }

    private static boolean checkPassword(String name, String password) {

        try {
            rustarWebService = new FindWebService(name, password).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (rustarWebService.getString(codeVariableName).equals(correctCode))
                return true;
            else
                return false;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isAccauntBlocked(String name, String password)  {
        accauntBlocked = false;

        return accauntBlocked;
    }



    public static void searchRelust(String checkIn, int amountOfNight, String checkOut, String location, String hotel, String pax) {}

    public static Hotel advancedSearchRelust(String checkIn, int amountOfNight, String checkOut, String hotelname, String city, String district, int amountOfAdults, ArrayList<Spinner> childrensAgeSpinner, int dailyFrom, int dailyTo, int totalFrom, int totalTo, String type, String meal, boolean fiveStar, boolean fourStar, boolean threeStar, boolean twoStar, boolean oneStar, boolean apartment, boolean alcohol, boolean freeWifi, boolean pool, boolean metro, boolean mall, Calendar checkInCal, Calendar checkOutCal) {
        int minPrice = dailyFrom == 0 ? totalFrom == 0 ? 0 : (totalFrom/amountOfNight) : dailyFrom;
        int maxPrice = dailyTo == 0 ? totalTo == 0 ? 0 : (totalTo/amountOfNight) : dailyTo;
        boolean[] stars = new boolean[] { oneStar, twoStar, threeStar, fourStar, fiveStar };

        if (hotelname.equals(""))
            hotelname = "Rixsos";
        Hotel findHotel = new Hotel(new String[] { firstHotel, secondHotel }, maxPrice, minPrice, checkIn, checkOut, hotelname, stars, amountOfNight, checkInCal, checkOutCal, meal);

        return findHotel;
    }


    public static class FindWebService extends AsyncTask<String, Void, JSONObject> {

        String name;
        String password;

        public FindWebService(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            URL url;
            String JSONCode;
            JSONObject mainObject = null;
            BufferedReader reader = null;
            HttpsURLConnection connection = null;

            try {

                url = new URL(WebServiceUrl + WebSName + name + WebSAnd + WebSPassword + password);
                connection = (HttpsURLConnection) url.openConnection();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer sBuffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    sBuffer.append(line);
                }

                JSONCode = sBuffer.toString();

                // Now we have our json code so lets create a json from this

                mainObject = new JSONObject(JSONCode);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return mainObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
        }
    }
}