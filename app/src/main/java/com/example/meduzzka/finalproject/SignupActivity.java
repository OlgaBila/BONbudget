package com.example.meduzzka.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents a SignUp Activity where user registered
 *
 * Created by Olga Bila
 */
public class SignupActivity extends AppCompatActivity {
    /**
     * Used to hold user name
     */
    EditText nameText;

    /**
     *
     * Used to hold user email
     */
    EditText emailText;

    /**
     * Used to hold user password
     */
    EditText passwordText;

    /**
     * Used to hold user password confirmation
     */
    EditText confirmPasswordText;

    /**
     * Button object, used for user signUp
     */
    Button signupButton;

    /**
     * TextView object, used for transferring to LogIn Activity
     */
    TextView loginLink;

    /**
     * Object of LoginDataBaseAdapter, used for retrieving and updating database
     */
    LoginDataBaseAdapter loginDataBaseAdapter;

    /**
     * The system calls this when creating the activity
     *
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     *                           onCreate method of every Android Activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameText = (EditText) findViewById(R.id.input_name);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        confirmPasswordText = (EditText) findViewById(R.id.confirm_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    /**
     * Called after signUpButton is pressed
     * Used for updating database
     */
    public void signup() {
        String userName = nameText.getText().toString();
        String userEmail = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        if (userName.equals("") || userEmail.equals("") || password.equals("")
                || confirmPassword.equals("")) {

            Toast.makeText(getApplicationContext(), R.string.fieldVaccant,
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {

            Toast.makeText(getApplicationContext(), R.string.enterValidEmail,
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 4 || password.length() > 10) {

            Toast.makeText(getApplicationContext(), R.string.passwordChar,
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(),
                    R.string.passwordNoMatch, Toast.LENGTH_LONG).show();
            return;
        } else {

            /**
             * Inserts data in database
             */
            loginDataBaseAdapter.insertEntry(userName, userEmail, password);
            /**
             * Show tost if accound created successfully
             */
            Toast.makeText(getApplicationContext(),
                    R.string.accountCreated, Toast.LENGTH_LONG).show();
            SignupActivity.this.startActivity(new Intent(SignupActivity.this, Profile.class));
            finish();

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
}