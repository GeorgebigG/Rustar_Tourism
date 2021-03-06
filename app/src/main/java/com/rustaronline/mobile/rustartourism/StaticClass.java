package com.rustaronline.mobile.rustartourism;

import android.os.AsyncTask;
import android.widget.Spinner;

import com.rustaronline.mobile.rustartourism.CallBack.OnShowResult;
import com.rustaronline.mobile.rustartourism.Searches.AdvancedSearch;

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

    public static String WebServiceUrl = "https://restapi.rustaronline.com/v1.00/api/";
    public static String HotelListName = "hotels?";
    public static String HotelPriceName = "hotelprices?";
    public static String RoomAvailabilitiesName = "roomavailabilities?";
    public static String URLAnd = "&";
    public static String WebSName = "agentid=";
    public static String WebSPassword = "agentpassword=";
    public static String WebSHotelId = "hotelid=";

    public static JSONObject rustarWebService = null;
    public static String rustarWebServiceCode = null;
    public static String codeVariableName = "Code";
    public static String correctCode = "00";

    public static String correctPassword = "Correct";
    public static String pasOrUsWrong = "password or username wrong";
    public static String usBlocked = "User Blocked";

    public static void searchAccount(String name, String password, OnShowResult showResult) {
        if (name.equals("") || password.equals("")) {
            showResult.showResult(pasOrUsWrong);
            return;
        }

        new FindWebService(name, password, showResult).execute();
    }


    public static void setRustarWebService(String code) {
        try {
            rustarWebService = new JSONObject(code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static boolean checkPassword(String name, String password) {

        if (name.equals("") || password.equals(""))
            return false;

        try {
            if (rustarWebService != null) {
                if (rustarWebService.getString(codeVariableName).equals(correctCode))
                    return true;
                else
                    return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isAccauntBlocked(String name, String password)  {
        accauntBlocked = false;

        return accauntBlocked;
    }



    public static void searchReslust(String checkIn, int amountOfNight, String checkOut, String location, String hotel, String pax) {}


    public static class FindWebService extends AsyncTask<String, Void, JSONObject> {

        String name;
        String password;
        OnShowResult showResult;

        public FindWebService(String name, String password, OnShowResult showResult) {
            this.name = name;
            this.password = password;
            this.showResult = showResult;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            URL url;
            String JSONCode = "";
            JSONObject mainObject = null;
            BufferedReader reader = null;
            HttpsURLConnection connection = null;

            try {

                url = new URL(WebServiceUrl + HotelListName + WebSName + name + URLAnd + WebSPassword + password);
                connection = (HttpsURLConnection) url.openConnection();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer sBuffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    JSONCode += line;
                }

                // Now we have our json code so lets create a json from this
                rustarWebServiceCode = JSONCode;
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
            rustarWebService = result;

            boolean correctPassword = checkPassword(name, password);

            if (correctPassword && !isAccauntBlocked(name, password)) {
                showResult.showResult(StaticClass.correctPassword);
                return;
            }
            else if (!correctPassword) {
                showResult.showResult(StaticClass.pasOrUsWrong);
                return;
            }
            else {
                showResult.showResult(StaticClass.usBlocked);
            }
        }
    }
}