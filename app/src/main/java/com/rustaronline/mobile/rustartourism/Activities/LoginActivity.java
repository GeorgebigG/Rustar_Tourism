package com.rustaronline.mobile.rustartourism.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.rustaronline.mobile.rustartourism.Helper.AnimationClass;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.StaticClass;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button signIn;
    EditText Username, Password;
    public static boolean destroyActivity = false;
    SharedPreferences sPref;
    public static final String USERNAME_KEY = "_username";
    public static boolean logOutClicked = false;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("     LOG IN");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_partners_rustar);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        AnimationClass.setAnimation(signIn, getResources().getColor(R.color.rustarGreen), getResources().getColor(R.color.clickColour));

        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!destroyActivity) {
            new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.exit))
                    .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginActivity.super.onBackPressed();
                        }
                    }).setNegativeButton(getResources().getString(R.string.No), null).create().show();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View v) {

        progressBar.setVisibility(View.VISIBLE);

        String result = StaticClass.getString(Username.getText().toString(), Password.getText().toString());


        if (result.equals("Correct")) {
            if (isConnected()) {
                saveUsername(Username.getText().toString());
                FirstpageActivity.Username = Username.getText().toString();
                Intent intent = new Intent(this, FirstpageActivity.class);
                destroyActivity = true;

                startActivity(intent);
                onBackPressed();
            } else
                new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.noInternetConnectionTextTitle))
                        .setMessage(getResources().getString(R.string.noInternetConnectionText))
                        .setNegativeButton(getResources().getString(R.string.Ok), null).setCancelable(false).create().show();
        }
        else if (result.equals("password or username wrong")) {
            new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.IncorrectPasswordTitle))
                    .setMessage(getResources().getString(R.string.IncorrectPasswordText)).setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.Ok), null).create().show();
        }
        else {
            new AlertDialog.Builder(this).setMessage(result).setNegativeButton(getResources().getString(R.string.Ok), null)
                    .setCancelable(false).create().show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            return true;
        else
            return false;
    }

    private void saveUsername(String username) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(USERNAME_KEY, username);
        ed.commit();
    }

    private String getUsername() {
        try {
            sPref = getPreferences(MODE_PRIVATE);
            String username = sPref.getString(USERNAME_KEY, "");
            return username;
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (isConnected()) {
//            if (!getUsername().equals("") && !logOutClicked) {
//                FirstpageActivity.Username = getUsername();
//                Intent intent = new Intent(this, FirstpageActivity.class);
//                destroyActivity = true;
//                startActivity(intent);
//                onBackPressed();
//            }
//        } else {
//            new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.noInternetConnectionTextTitle))
//                    .setMessage(getResources().getString(R.string.noInternetConnectionText))
//                    .setNegativeButton(getResources().getString(R.string.Ok), null).setCancelable(false).create().show();
//        }
    }
}
