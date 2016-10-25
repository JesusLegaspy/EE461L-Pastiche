package com.pastiche.pastiche;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class MainActivity extends AppCompatActivity {

    private Toolbar main_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) ); //TODO: remove??

        main_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(main_toolbar);
        TextView app_name = (TextView) findViewById(R.id.toolbar_app_name);
        Typeface title_font = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.ttf");

        app_name.setTypeface(title_font);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the option main_menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noInspection SimplifiableIfStatement
        if ( id == R.id.action_settings ){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void capturePicture(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
//-------------------[TESTING]------------------------
    public void testLogin(View view) throws JSONException {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loginTest();
            imageTest();
        } else {
            // display error
            Context context = getApplicationContext();
            CharSequence text = "No internet connection!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    TextView mTxtDisplay;
    private void loginTest () throws JSONException {
        mTxtDisplay = (TextView) findViewById(R.id.responseText);

        JSONObject body = new JSONObject();
        body.put("username","myname");
        body.put("password","mypassword");
        body.put("email","myemail@no.co");

        Response.Listener<JSONObject> successful = new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mTxtDisplay.setText("Response: " + response.toString());

                    }

                };

        Response.ErrorListener errored = new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        mTxtDisplay.setText("Response: " + error.toString());
                    }
                };

        ServerRequestHandler.createAccountRequest(this.getApplicationContext(), body, successful, errored);
    }
    ImageView mImageView;
    private void imageTest(){
        String url = "http://api.pastiche.staging.jacobingalls.rocks/photos/14";
        mImageView = (ImageView) findViewById(R.id.imageView);

// Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        mImageView.setImageResource(R.drawable.magnify);
                    }
                });
// Access the RequestQueue through your singleton class.
        // Access the RequestQueue through your singleton class.
        ServerRequestQueue test = ServerRequestQueue.getInstance(this.getApplicationContext());
        test.addToRequestQueue(request);
    }
}