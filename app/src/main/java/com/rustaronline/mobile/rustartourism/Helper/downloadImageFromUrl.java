package com.rustaronline.mobile.rustartourism.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.Display;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rustaronline.mobile.rustartourism.Activities.FirstpageActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (isImageButton) {
            imageButton.setImageBitmap(result);
            float widthScale = (float) FirstpageActivity.WIDTH / (float) result.getWidth();
            float imageHeight = (float) result.getHeight() * widthScale;
            imageButton.setMaxHeight((int) imageHeight);
        }
        else {
            imageView.setImageBitmap(result);
            float widthScale = (float) FirstpageActivity.WIDTH / (float) result.getWidth();
            float imageHeight = (float) result.getHeight() * widthScale;
            imageView.setMinimumHeight((int) imageHeight);
        }
    }
}
