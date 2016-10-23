package com.pastiche.pastiche;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    private Toolbar main_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


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
    public void testLogin(View view){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            ServerRequestHandler test = ServerRequestHandler.getInstance(this.getApplicationContext());
            test.addToRequestQueue(getDummyObjectArrayWithPost(createMyReqSuccessListener(), createMyReqErrorListener()));

        } else {
            // display error
            Context context = getApplicationContext();
            CharSequence text = "No internet connection!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * An example call (not used in this example app) to demonstrate how to do a Volley POST call
     * and parse the response with Gson.
     *
     * @param listener is the listener for the success response
     * @param errorListener is the listener for the error response
     *
     */
    public static GsonPostRequest getDummyObjectArrayWithPost
    (
            Response.Listener<PUser> listener,
            Response.ErrorListener errorListener
    )
    {
        final String url = "http://api.pastiche.staging.jacobingalls.rocks/users/login";

        /*final Gson gson = new GsonBuilder()
                .registerTypeAdapter(PUser.class, new DummyObjectDeserializer())
                .create();*/
        final Gson gson = new Gson();

        final Gson test = new Gson();
        PUser yes = new PUser(1,"myname","myemail@no.co","mypassword");
        String jsonObject;
        jsonObject = gson.toJson(yes);

       return new GsonPostRequest<>
                (
                        url,
                        jsonObject,
                        new TypeToken<PUser>()
                        {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    private Response.Listener<PUser> createMyReqSuccessListener() {
        return new Response.Listener<PUser>() {
            @Override
            public void onResponse(PUser response) {
                // Do whatever you want to do with response;
                // Like response.tags.getListing_count(); etc. etc.
                Context context = getApplicationContext();
                CharSequence text = "Logged in!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                Context context = getApplicationContext();
                CharSequence text = "Response Error";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };
    }

}