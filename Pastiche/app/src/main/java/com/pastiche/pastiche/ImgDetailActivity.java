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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pastiche.pastiche.server.ServerHandler;
import com.pastiche.pastiche.server.ServerRequestHandler;

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
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        setupActivity(savedInstanceState);
    }



    private void setupActivity(Bundle savedInstanceState) {
        Toolbar img_toolbar = (Toolbar) findViewById(R.id.photo_detail_toolbar);
//        setSupportActionBar(img_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            img_toolbar.setBackgroundColor(getColor(R.color.colorAccentPink));
        }


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
        imgUserId.setText(username);

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
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView);
    }



    private void retrieveData(Bundle savedInstanceState) {
        this.photoId = retrieveId(savedInstanceState, EXTRA_PHOTO_ID);
        this.eventId = retrieveId(savedInstanceState, EXTRA_EVENT_ID);
        this.imgUserId = retrieveId(savedInstanceState, EXTRA_IMG_USER_ID);
        this.uploadDate = retrieveString(savedInstanceState, EXTRA_IMG_UPLOAD);
        this.curUserId = getCurUserId();

        ServerHandler.getInstance(getApplicationContext()).getUser(this.imgUserId,
                data -> {
                    this.username = data.getUsername();
                    bindViews();
                },
                error -> {
                    this.username = "Unknown (Error: "+error+")";
                    bindViews();
                });
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
        return preferences.getInt("userId", -1);
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
                        this::onRemoveSuccess,
                        this::onRemoveFail
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
