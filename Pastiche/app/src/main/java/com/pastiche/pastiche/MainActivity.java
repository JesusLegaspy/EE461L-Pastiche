package com.pastiche.pastiche;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.BitmapFactory;
        import android.graphics.Typeface;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.android.volley.toolbox.ImageLoader;

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
    public void testLogin(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //do work
            ServerHandler handle = ServerHandler.getInstance(getApplicationContext());
            handle.login("kqtest", "mypassword", x -> Log.d("Main_KhalidTesting", "ID: " + x.getId() + " UserID: " + x.getUserId()), x -> Log.d("Main_KhalidTesting",x));
            testImage();
        } else {
            //log
        }
    }

    public void testImage() {
        ServerHandler handler = ServerHandler.getInstance(getApplicationContext());


        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.regularImageView);
        //mImageView.setImageBitmap();


        handler.getImg(27, ImageView.ScaleType.CENTER, x -> mImageView.setImageBitmap(x), x -> Log.d("main", "Didn't work. " + x));
    }

    //--------------------[Cache Testing]---------------------
    private void testCache() {
    }
}