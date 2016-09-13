package com.rustaronline.mobile.rustartourism.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rustaronline.mobile.rustartourism.CallBack.OnShowResult;
import com.rustaronline.mobile.rustartourism.Helper.AnimationClass;
import com.rustaronline.mobile.rustartourism.R;
import com.rustaronline.mobile.rustartourism.StaticClass;
import com.rustaronline.mobile.rustartourism.YouTube.YouTube;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnShowResult {
    Button signIn;
    EditText username, password;

    public static boolean destroyActivity = false;
    SharedPreferences sPref;

    public static final String PATH = "Rustar/Data/";
    public static final String USERNAME_KEY = "_username";
    public static final String PASSWORD_KEY = "_password";
    public static boolean logOutClicked = false;

    public ProgressDialog pd;

    public static int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("     " + getResources().getString(R.string.LOG_IN));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_partners_rustar);

        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        AnimationClass.setAnimation(signIn, getResources().getColor(R.color.rustarGreen), getResources().getColor(R.color.clickColour));

        username = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.Password);

        pd = new ProgressDialog(LoginActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.wait));
        pd.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.help)
            startActivity(new Intent(this, YouTube.class));

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
        if (isConnected()) {
            pd.show();
            StaticClass.searchAccount(username.getText().toString(), password.getText().toString(), this);
        }
        else
            noInternetConnection();
    }

    public void showResult(String result) {
        pd.cancel();

        if (result.equals(StaticClass.correctPassword)) {
            saveUsername(username.getText().toString());

            FirstpageActivity.Username = username.getText().toString();
            FirstpageActivity.Password = password.getText().toString();

            saveUsername(username.getText().toString());
            savePassword(password.getText().toString());

            Intent intent = new Intent(this, FirstpageActivity.class);
            destroyActivity = true;

            startActivity(intent);
            onBackPressed();
        }
        else if (result.equals(StaticClass.pasOrUsWrong)) {
            new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.incorrect_password_title))
                    .setMessage(getResources().getString(R.string.incorrect_password_text)).setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.Ok), null).create().show();

            username.setText("");
            password.setText("");
        }
        else {
            new AlertDialog.Builder(this).setMessage(result).setNegativeButton(getResources().getString(R.string.Ok), null)
                    .setCancelable(false).create().show();
        }

        signIn.setText(getResources().getString(R.string.signIn));
        signIn.setBackgroundColor(getResources().getColor(R.color.rustarGreen));

    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            return true;
        else
            return false;
    }

    private void saveUsername(String username) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PATH + USERNAME_KEY, username);
        ed.commit();
    }

    private String getUsername() {
        try {
            sPref = getPreferences(MODE_PRIVATE);
            String username = sPref.getString(PATH + USERNAME_KEY, "");
            return username;
        } catch (Exception ex) {
            return "";
        }
    }

    private void savePassword(String password) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PATH + PASSWORD_KEY, password);
        ed.commit();
    }

    private String getPassword() {
        try {
            sPref = getPreferences(MODE_PRIVATE);
            String password = sPref.getString(PATH + PASSWORD_KEY, "");
            return password;
        } catch (Exception ex) {
            return  "";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isConnected()) {
            String username = getUsername();
            String password = getPassword();

            if (!username.equals("") && !password.equals("") && !logOutClicked) {
                this.username.setText(username);
                this.password.setText(password);

                pd.show();
                StaticClass.searchAccount(username, password, this);
            }
            else if (logOutClicked) {
                saveUsername("");
                savePassword("");
            }

        } else
            noInternetConnection();
    }

    public void noInternetConnection() {
        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.no_internet_connection_text_title))
                .setMessage(getResources().getString(R.string.no_internet_connection_text))
                .setNegativeButton(getResources().getString(R.string.Ok), null).setCancelable(false).create().show();
    }
}
