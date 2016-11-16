package com.pastiche.pastiche.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pastiche.pastiche.MainActivity;
import com.pastiche.pastiche.PObject.PSession;
import com.pastiche.pastiche.R;
import com.pastiche.pastiche.Server.ServerHandler;


/**
 * Login screen allows users to login with their username and password
 * <p>
 * Created by Aria Pahlavan on 10/16/16.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;
    private String password;
    private String email;
    protected ServerHandler serverReqest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.serverReqest = ServerHandler.getInstance(this.getApplicationContext());

        //make Navigation bar transparent with bg color
        //set status bar color
        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.windowBackground));
        }

        //Credentials provided by user
        emailText = (EditText) findViewById(R.id.input_login_email);
        passwordText = (EditText) findViewById(R.id.input_login_password);
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
                if ( validateLoginFormat(false) ) {
                    MainActivity.enableButton(loginButton);
                } else {
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
                if ( validateLoginFormat(false) ) {
                    MainActivity.enableButton(loginButton);
                } else {
                    MainActivity.disableButton(loginButton);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //if invalid email and password -> display error
        emailText.setOnFocusChangeListener((v, hasFocus) -> validateLoginFormat(true));

        emailText.setOnFocusChangeListener((v, hasFocus) -> validateLoginFormat(true));

        passwordText.setOnFocusChangeListener((v, hasFocus) -> validateLoginFormat(true));


        //User tapped on login button -> process login
        loginButton.setOnClickListener(v -> login());

        //User tapped on "create new account" link -> switch to SignupActivity
        signupLink.setOnClickListener(v -> {

            Intent signUpIntent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(signUpIntent, REQUEST_SIGNUP);
        });
    }


    /**
     * Attempts to login using credentials provided by user through server req handler
     */
    protected void login() {
        Log.d(ACTIVITY_TAG, "Login in progress!");
        MainActivity.disableButton(loginButton);
        updateAttr();


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("hang tight (ᵔᴥᵔ)");
        progressDialog.show();


        //TODO call Jesus' function, Frank
        serverReqest.login( this.email,
                            this.password,
                            pSession->{
                                onLoginSuccess(pSession);
                                progressDialog.dismiss();
                            },
                            error->{onLoginFail(error);
                                    progressDialog.dismiss();
                            });

        new android.os.Handler().postDelayed(() -> progressDialog.dismiss(), 4000);
    }

    /**
     * After a successful login session, finish activities
     * @param pSession
     */
    private void onLoginSuccess(PSession pSession) {
        loginButton.setEnabled(true);
        int numTries = 3;
        //ensure user info is stored on device
        while ( !storeLoginUserInfo(pSession) ) {
            if ( numTries == 0 ) {
                Log.d(ACTIVITY_TAG, "CANNOT STORE USER INFO");
                break;
            }
            numTries--;
        }

        Toast.makeText(getBaseContext(), "Login was successful", Toast.LENGTH_SHORT).show();


        this.finish();
    }

    private boolean storeLoginUserInfo(PSession pSession) {
        SharedPreferences preferences = getSharedPreferences(MainActivity.getSharedPreferenceName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("id", String.valueOf(pSession.getId()));
        editor.putBoolean("logged_in", true);
        return editor.commit();
    }


    /**
     * If login not successful, display a toast message and allow user to retry
     * @param error
     */
    private void onLoginFail(String error) {
        loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        //user won't be able to nav back to main, exit app instead
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_SIGNUP ) {
            if ( resultCode == RESULT_OK ) {

                // TODO: put successful signup logic here
                //TODO remove finish
                this.finish();
            }
        }
    }


    /**
     * verifies whether email and password have valid format
     *
     * @param displayError
     *         if true an error will be displayed specifying
     *         why the input is invalid
     * @return returns true if both password and email are valid, else returns false
     */
    protected boolean validateLoginFormat(boolean displayError) {
        updateAttr();
        boolean isValidPass = true;
        boolean isValidEmail = true;

        //TODO add an error drawable icon
        //invalid pass length
        if ( password.length() < 8 && !password.isEmpty() ) {
            if ( displayError )
                passwordText.setError("Oops! Password must be at least 8 characters");
            isValidPass = false;
        } else if ( password.isEmpty() ) {
            isValidPass = false;
        } else {
            //remove errors
            passwordText.setError(null);
            isValidPass = true;
        }

        //invalid email
//        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty()){
//            if ( displayError )
//                emailText.setError("Please enter an email address.");
//            isValidEmail = false;
//        }
//        else

        //invalid email or username
        if ( email.isEmpty() ) {
            emailText.setError(null);
            isValidEmail = false;
        } else {
            //remove errors
            emailText.setError(null);
            isValidEmail = true;
        }

        return isValidEmail & isValidPass;
    }

    /**
     * updates login attributes
     */
    private void updateAttr() {
        this.password = passwordText.getText().toString();
        this.email = emailText.getText().toString();
    }

}


