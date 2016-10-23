package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Sign up screen allows guests to sing up with a username, email, and password
 *
 * Created by Aria Pahlavan on 10/17/16.
 */
public class SignupActivity extends AppCompatActivity {
    private static final String  ACTIVITY_TAG = "SignupActivity";

    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private Button signupButton;
    private TextView loginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Credentials provided by user
        usernameText = (EditText) findViewById(R.id.input_username);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button) findViewById(R.id.btn_login);
        loginLink = (TextView) findViewById(R.id.link_signup);

        MainActivity.disableButton(signupButton);

        //check if valid password to enable signup button
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if valid email, password and username -> enable signup button
                if( validateSignupFormat(false)){
                    MainActivity.enableButton(signupButton);
                }
                else {
                    Log.d(ACTIVITY_TAG, "invalid signup info");
                    MainActivity.disableButton(signupButton);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //check if valid username to enable signup button
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if valid email, password and username-> enable signup button
                if( validateSignupFormat(false)){
                    MainActivity.enableButton(signupButton);
                }
                else {
                    Log.d(ACTIVITY_TAG, "invalid signup info");
                    MainActivity.disableButton(signupButton);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //check if valid email to enable signup button
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if valid email, password and username -> enable signup button
                if( validateSignupFormat(false)){
                    MainActivity.enableButton(signupButton);
                }
                else {
                    Log.d(ACTIVITY_TAG, "invalid signup info");
                    MainActivity.disableButton(signupButton);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //if invalid email, password and username -> display error
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if invalid email, password and username -> display error
                validateSignupFormat(true);
            }
        });

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if invalid email and password -> display error
                validateSignupFormat(true);
            }
        });

        usernameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if invalid email and password -> display error
                validateSignupFormat(true);
            }
        });


        //User tapped on signup button -> process signup
        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signup();
            }
        });

        //User tapped on "create new account" link -> switch to Signup Activity
        loginLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // finish the Signup activity and return to login screen
                finish();
            }
        });
    }


    private void signup() {
        //TODO implement signup algorithm
    }


    /**
     * After a successful signup session, finish activities
     */
    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }


    /**
     * If signup not successful, display a toast message and allow user to retry
     */
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    /**
     * verifies whether username, email and password have valid format
     * @param displayError if true an error will be displayed specifying
     *                     why the input is invalid
     * @return returns true if both username, password and email are valid, else returns false
     */
    protected boolean validateSignupFormat(boolean displayError){
        String password = passwordText.getText().toString();
        String email = emailText.getText().toString();
        String username = usernameText.getText().toString();
        boolean isValidPass = true;
        boolean isValidEmail = true;

        //TODO add an error drawable icon
        //invalid pass length
        if(password.length() < 8 && !password.isEmpty()){
            if ( displayError )
                passwordText.setError("Oops! Password must be at least 8 characters");
            isValidPass = false;
        }
        else if ( password.isEmpty() ){
            isValidPass = false;
        }
        else {
            //remove errors
            passwordText.setError(null);
            isValidPass = true;
        }

        //invalid email
        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty()){
            if ( displayError )
                emailText.setError("Please enter an email address.");
            isValidEmail = false;
        }
        else if ( email.isEmpty() ){
            emailText.setError(null);
            isValidEmail = false;
        }
        else{
            //remove errors
            emailText.setError(null);
            isValidEmail = true;
        }


        //invalid username
        if ( username.isEmpty() ){
            usernameText.setError(null);
            isValidEmail = false;
        }
        else{
            //remove errors
            usernameText.setError(null);
            isValidEmail = true;
        }

        return isValidEmail & isValidPass;
    }
}

