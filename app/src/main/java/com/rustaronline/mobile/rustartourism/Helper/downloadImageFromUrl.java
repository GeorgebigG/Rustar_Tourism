package com.rustaronline.mobile.rustartourism.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.Display;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rustaronline.mobile.rustartourism.Activities.FirstpageActivity;
import com.rustaronline.mobile.rustartourism.Activities.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gio on 5/23/16.
 */
public class downloadImageFromUrl extends AsyncTask<Void, Void, Bitmap> {
    String url;
    ImageButton imageButton;
    ImageView imageView;
    boolean isImageButton;

    public downloadImageFromUrl(ImageButton imageView, String url) {
        this.imageButton = imageView;
        this.url = url;
        isImageButton = true;
    }

    public downloadImageFromUrl(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
        isImageButton = false;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        InputStream input = null;

        try {
            URL urlConnection = new URL(url);
            connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();

            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (isImageButton) {
            imageButton.setImageBitmap(result);
            float widthScale = (float) LoginActivity.screenWidth / (float) result.getWidth();
            float imageHeight = (float) result.getHeight() * widthScale;
            imageButton.setMaxHeight((int) imageHeight);
        }
        else {
            imageView.setImageBitmap(result);
            float widthScale = (float) LoginActivity.screenHeight / (float) result.getWidth();
            float imageHeight = (float) result.getHeight() * widthScale;
            imageView.setMinimumHeight((int) imageHeight);
        }
    }
}
