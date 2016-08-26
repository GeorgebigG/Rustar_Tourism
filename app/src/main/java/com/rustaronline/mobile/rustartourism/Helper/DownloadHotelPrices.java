package com.rustaronline.mobile.rustartourism.Helper;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by georgenebieridze on 8/26/16.
 */
public class DownloadHotelPrices extends AsyncTask<String, Void, JSONObject> {
    String url;
    JSONObject jsObject;

    public DownloadHotelPrices(String url, @Nullable JSONObject object) {
        this.url = url;
        this.jsObject = object;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url;
        HttpsURLConnection connection = null;
        BufferedReader reader = null;
        String code = "";

        try {

            url = new URL(this.url);
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                code += line;
            }

            jsObject = new JSONObject(code);

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

        return jsObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
