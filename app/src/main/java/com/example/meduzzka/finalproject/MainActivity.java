package com.example.meduzzka.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This class represents a MainActivity
 *
 * Created by Olga Bila
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Static integer that represents request for sign up
     */
    private static final int REQUEST_SIGNUP = 0;

    /**
     * Button object, used for transport user to LogIn activity
     */
    Button buttonSignIn;

    /**
     * Button object, used for transport user to SignUp activity
     */
    Button buttonSignUp;

    /**
     * The system calls this when creating the activity
     *
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     *                           onCreate method of every Android Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        });

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }

        });
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode the integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from
     * @param resultCode the integer result code returned by the child activity
     *                   through its setResult()
     * @param data an Intent, which can return result data to the caller
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    /**
     * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
     * for your activity to start interacting with the user
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped,
     * but is now again being displayed to the user
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Called as part of the activity lifecycle when an activity is going
     * into the background, but has not (yet) been killed
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Called when you are no longer visible to the user
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Perform any final cleanup before an activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
