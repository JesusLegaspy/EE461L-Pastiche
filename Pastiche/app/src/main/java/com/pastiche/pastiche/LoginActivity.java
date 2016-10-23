package com.pastiche.pastiche;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
 * Login screen allows users to login with their username and password
 *
 * Created by Aria Pahlavan on 10/16/16.
 */
public class LoginActivity extends AppCompatActivity{
    private static final String  ACTIVITY_TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;



    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Credentials provided by user
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);

        MainActivity.disableButton(loginButton);

        //check if valid password to enable login button
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if valid email and password -> enable login button
                if( validateLoginFormat(false)){
                    MainActivity.enableButton(loginButton);
                }
                else {
                    Log.d(ACTIVITY_TAG, "invalid login info");
                    MainActivity.disableButton(loginButton);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //check if valid email to enable login button
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if valid email and password -> enable login button
                if( validateLoginFormat(false)){
                    MainActivity.enableButton(loginButton);
                }
                else {
                    Log.d(ACTIVITY_TAG, "invalid login info");
                    MainActivity.disableButton(loginButton);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //if invalid email and password -> display error
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if invalid email and password -> display error
                validateLoginFormat(true);
            }
        });

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if invalid email and password -> display error
                validateLoginFormat(true);
            }
        });


        //User tapped on login button -> process login
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        //User tapped on "create new account" link -> switch to SignupActivity
        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent signUpIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(signUpIntent, REQUEST_SIGNUP);
            }
        });
    }




    /**
     * Attempts to login using credentials provided by user
     */
    protected void login() {
        Log.d(ACTIVITY_TAG, "Login in progress!");
        MainActivity.disableButton(loginButton);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("hang tight (ᵔᴥᵔ)");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //TODO call Jesus's function,Frank

        //
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }

                }, 3000);


    }

    /**
     * After a successful login session, finish activities
     */
    private void onLoginSuccess() {
        loginButton.setEnabled(true);

        Toast.makeText(getBaseContext(), "Login was successful", Toast.LENGTH_LONG).show();


        this.finish();
    }


    /**
     * If login not successful, display a toast message and allow user to retry
     */
    private void onLoginFail() {
        loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Oops! Login failed", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        //user won't be able to go back to slash activity
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: put successful signup logic here
                //TODO remove finish
                this.finish();
            }
        }
    }



    /**
     * verifies whether email and password have valid format
     * @param displayError if true an error will be displayed specifying
     *                     why the input is invalid
     * @return returns true if both password and email are valid, else returns false
     */
    protected boolean validateLoginFormat(boolean displayError){
        String password = passwordText.getText().toString();
        String email = emailText.getText().toString();
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

        return isValidEmail & isValidPass;
    }

}


