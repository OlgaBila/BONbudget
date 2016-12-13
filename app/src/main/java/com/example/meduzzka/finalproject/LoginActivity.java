package com.example.meduzzka.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class represents a Login Activity where user needs to enter email and password
 *
 * Created by Olga Bila
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Static integer that represents request for sign up
     */
    private static final int REQUEST_SIGNUP = 0;

    /**
     * Object of LoginDataBaseAdapter, used for retrieving and updating database
     */
    LoginDataBaseAdapter loginDataBaseAdapter;

    /**
     * ProgressBar object, used to show progress bar while downloading an image
     */
    private ProgressBar progressBar;

    /**
     * ImageView object, used to show an image for Login page
     */
    ImageView imageView;

    /**
     * Bitmap object, used for representation of a bitmap image
     */
    Bitmap imageBitmap;

    /**
     * URL of image that need to be download
     */
    String url = "http://lh3.ggpht.com/hlPm_BqoMc-mLDC4mBGMmv9_lNa0vR3V8XCXj_cnefD0zBtbsnTFm37OWLghm2Or3Us=w350";

    /**
     * Button object, used for user logIn
     */
    Button loginButton;

    /**
     * TextView object, used for transferring to SignUp Activity
     */
    TextView signupLink;

    /**
     * The system calls this when creating the activity
     *
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     *                           onCreate method of every Android Activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        imageView = (ImageView) findViewById(R.id.imageView);
        new ImageLoaderClass().execute(url);

        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /** Start the SignUp activity*/
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    /**
     * Called after loginButton is pressed
     * Used for checking email and password with data in database
     */
    public void login() {
        final EditText userEmail = (EditText) findViewById(R.id.input_email);
        final EditText userPassword = (EditText) findViewById(R.id.input_password);

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String storedPassword = loginDataBaseAdapter.getSinlgeEntry(email);

        /** Checking email and password with data in database*/
        if (password.equals(storedPassword)) {
            Toast.makeText(LoginActivity.this,
                    R.string.loginSuccessfull, Toast.LENGTH_LONG)
                    .show();
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, Profile.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this,
                    R.string.noMatch,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called on destroy of Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    /**
     * Helper class for downloading an image
     */
    private class ImageLoaderClass extends AsyncTask<String, String, Bitmap> {

        /**
         * Runs on the UI thread before doInBackground(Params...)
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Performs a computation on a background thread
         * @param args the parameters of the task
         * @return Bitmap object
         */
        protected Bitmap doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                /** Starts the query*/
                conn.connect();
                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    InputStream stream = conn.getInputStream();

                    imageBitmap = BitmapFactory.decodeStream(stream);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return imageBitmap;
        }

        /**
         * Runs on the UI thread after doInBackground(Params...).
         * The specified result is the value returned by doInBackground(Params...)
         * @param image Bitmap object
         */
        protected void onPostExecute(Bitmap image) {

            if(image != null){
                if(image != null){
                    /**
                     * Sets Bitmap object into an ImageView object that used to
                     * show an image for Login page
                     */
                    imageView.setImageBitmap(image);
                    progressBar.setVisibility(View.GONE);

                }}
        }
    }
}