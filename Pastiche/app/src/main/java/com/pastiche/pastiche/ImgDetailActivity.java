package com.pastiche.pastiche;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.Server.ServerRequestHandler;

public class ImgDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PHOTO_ID = "photoId";
    public static final String EXTRA_EVENT_ID = "eventId";
    public static final String EXTRA_IMG_USER_ID = "imgUserID";
    public static final String EXTRA_IMG_UPLOAD = "imgUploadDate";
    public static final String ACTIVITY_TAG = "ImgDetailActivity";


    private int photoId;
    private int eventId;
    private int curUserId;
    private int imgUserId;
    private String uploadDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        setupActivity(savedInstanceState);
    }



    private void setupActivity(Bundle savedInstanceState) {
        Toolbar img_toolbar = (Toolbar) findViewById(R.id.photo_detail_toolbar);
        setSupportActionBar(img_toolbar);
        img_toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentPink));
        TextView app_name = (TextView) findViewById(R.id.txt_photo_det_toolbar);

        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentPink));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.windowBackgroundDarker));
        }

        retrieveData(savedInstanceState);
        bindViews();

    }

    private void bindViews() {
        ImageView photoView = (ImageView) findViewById(R.id.img_det_picture);
        setImage(photoView);

        TextView imgUserId = (TextView) findViewById(R.id.txt_username_img_detail);
        imgUserId.setText(String.valueOf(this.imgUserId)); // TODO: 11/25/16 change this to be the username of the photo owner

        TextView uploadDate = (TextView) findViewById(R.id.txt_upload_img_detail);
        uploadDate.setText(this.uploadDate);
    }



    /**
     * grab and set image to be displayed
     */
    private void setImage(ImageView photoView) {
        String internetUrl = ServerRequestHandler.baseURL + "/photos/";
        String url = internetUrl + this.photoId;
        Log.d("ImgDetailActivity", "photo picked: " + this.photoId);
        Glide.with(getApplicationContext())
                .load(url)
                .into(photoView);
    }



    private void retrieveData(Bundle savedInstanceState) {
        this.photoId = retrieveId(savedInstanceState, EXTRA_PHOTO_ID);
        this.eventId = retrieveId(savedInstanceState, EXTRA_EVENT_ID);
        this.imgUserId = retrieveId(savedInstanceState, EXTRA_IMG_USER_ID);
        this.uploadDate = retrieveString(savedInstanceState, EXTRA_IMG_UPLOAD);
        this.curUserId = getCurUserId();
    }


    /**
     * retrieve text based on TAG from callee (which is an photoListViewHolder)
     *
     * @param savedInstanceState
     * @param tag
     * @return
     */
    private String retrieveString(Bundle savedInstanceState, String tag) {
        String txt;

        if ( savedInstanceState == null ) {
            Bundle extras = getIntent().getExtras();
            if ( extras == null ) {
                txt = null;
            } else {
                txt = extras.getString(tag);
            }
        } else {
            txt = (String) savedInstanceState.getSerializable(tag);
        }

        return txt;
    }


    /**
     * retrieve id based on TAG from callee (which is an photoListViewHolder)
     *
     * @param savedInstanceState
     * @param tag
     * @return
     */
    private int retrieveId(Bundle savedInstanceState, String tag) {
        int id;

        if ( savedInstanceState == null ) {
            Bundle extras = getIntent().getExtras();
            if ( extras == null ) {
                id = -1;
            } else {
                id = extras.getInt(tag);
            }
        } else {
            id = (int) savedInstanceState.getSerializable(tag);
        }

        return id;
    }


    private int getCurUserId() {
        SharedPreferences preferences = getSharedPreferences(MainActivity.getSharedPreferenceName(), Context.MODE_PRIVATE);
        int v = preferences.getInt("userId", -1);
        return v;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if ( this.curUserId == this.imgUserId ) {
            //inflate the option main_menu
            getMenuInflater().inflate(R.menu.image_menu, menu);
            return true;
        } else return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noInspection SimplifiableIfStatement
        if ( id == R.id.action_remove ) {
            removeImage();
        }


        return super.onOptionsItemSelected(item);
    }



    private void removeImage() {

        ServerHandler.getInstance(getApplicationContext())
                .removePhotoFromEvent(
                        String.valueOf(photoId),
                        String.valueOf(eventId),
                        data -> onRemoveSuccess(data),
                        error -> onRemoveFail(error)
                        );
    }

    private void onRemoveFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Log.d(ACTIVITY_TAG, "couldn't remove photo!");
    }

    private void onRemoveSuccess(String data) {
        Toast.makeText(this, "you removed a photo :(", Toast.LENGTH_SHORT).show();
        Log.d(ACTIVITY_TAG, "success " + data);
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
