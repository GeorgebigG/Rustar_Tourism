package com.rustaronline.mobile.rustartourism.YouTube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rustaronline.mobile.rustartourism.Activities.FirstpageActivity;
import com.rustaronline.mobile.rustartourism.Activities.LoginActivity;
import com.rustaronline.mobile.rustartourism.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gio on 9/12/16.
 */
public class YouTubeImage extends AsyncTask<String, Void, Bitmap> {

    private String url;
    private ProgressBar progressBar;
    private ImageView image;
    private LinearLayout layout;
    private Context context;

    public YouTubeImage(String url, ProgressBar progressBar, ImageView imageView, LinearLayout layout, Context context) {
        this.url = url;
        this.progressBar = progressBar;
        this.image = imageView;

        this.layout = layout;
        setContext(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        HttpsURLConnection connection = null;
        Bitmap image = null;
        InputStream input = null;

        try {

            URL url = new URL(getUrl());
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            input = connection.getInputStream();
            image = BitmapFactory.decodeStream(input);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result == null)
            return;

        layout.removeView(progressBar);

        // make scaled image
        image.setImageBitmap(result);
        int widthScale = (LoginActivity.screenWidth - (int) getContext().getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) / result.getWidth();
        int imageHeight = result.getHeight() * widthScale;
        image.setMinimumHeight(imageHeight);
    }
}
