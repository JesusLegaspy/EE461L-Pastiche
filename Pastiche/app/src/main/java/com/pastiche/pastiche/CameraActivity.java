package com.pastiche.pastiche;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.pastiche.pastiche.Server.ServerHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jesus legaspy on 10/17/2016.
 */

enum REQ_RESULT {
    DENIED,
    GRANTED,
    WAIT
}

public class CameraActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";
    private REQ_RESULT permission_result = REQ_RESULT.WAIT;

    int bingo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        dispatchTakePictureIntent();

        bingo = retrieveEventId(savedInstanceState);
    }

    private static final String TAG = new String();

    // -------------- [Camera Interactions] ------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK ) {
            galleryAddPic();
            upload();
        }
        super.finish();
    }

    // -------------- [Private Camera Methods] ------------------
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        // Do some checking...
        checkSDCardState(); //todo work on sdcard handling
        isStoragePermissionGranted();

        if ( permission_result == REQ_RESULT.DENIED )
            finish();

        else if ( permission_result == REQ_RESULT.GRANTED )
            onPermissionGranted();
    }

    private void onPermissionGranted() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if ( takePictureIntent.resolveActivity(getPackageManager()) != null ) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                // ...
                Toast.makeText(this, "camera is not responsive", Toast.LENGTH_LONG).show();
                finishAfterTransition();
            }
            // Continue only if the File was successfully created
            if ( photoFile != null ) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.pastiche.pastiche.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void upload() {
        ServerHandler handler = ServerHandler.getInstance(getApplicationContext());
        if ( bingo >= 0 ) {
            handler.postImg(bingo, mCurrentPhotoPath, x -> Toast.makeText(getApplicationContext(), "Uploaded as pic " + x.toString(),
                    Toast.LENGTH_LONG).show(), x -> Toast.makeText(getApplicationContext(), x,
                    Toast.LENGTH_LONG).show());
        } else {
            handler.postImg(mCurrentPhotoPath, x -> Toast.makeText(getApplicationContext(), "Uploaded as pic " + x.toString(),
                    Toast.LENGTH_LONG).show(), x -> Toast.makeText(getApplicationContext(), x,
                    Toast.LENGTH_LONG).show());
        }
    }
    // -------------- [End of Camera Code] ------------------

    // --------------- [SD Card]------------------
    // Credit: "Melquiades" from https://stackoverflow.com/questions/20114206/android-failed-to-mkdir-on-sd-card
    private void checkSDCardState() {
        //made for debugging
        //TODO actually do something when SDcard is unavailable
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if ( Environment.MEDIA_MOUNTED.equals(state) ) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if ( Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    //----------------[API 23+ Proper Permissions]--------------
    //Credit: "MetaSnarf" from https://stackoverflow.com/questions/33162152/storage-permission-error-in-marshmallow

    private void isStoragePermissionGranted() {
        if ( Build.VERSION.SDK_INT >= 23 ) {
            if ( checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "Permission is granted");
                permission_result =  REQ_RESULT.GRANTED;
            } else {

                Log.d(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
                permission_result = REQ_RESULT.WAIT;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d(TAG, "Permission is granted");
            permission_result = REQ_RESULT.GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            onPermissionGranted();
        }
        else {
            onPermissionDenied();
        }
    }

    private void onPermissionDenied() {
        finish();
    }

    /**
     * retrieve event id from callee (which is an eventListViewHolder)
     *
     * @param savedInstanceState
     * @return
     */
    private int retrieveEventId(Bundle savedInstanceState) {
        int eventId;

        if ( savedInstanceState == null ) {
            Bundle extras = getIntent().getExtras();
            if ( extras == null ) {
                eventId = -1;
            } else {
                eventId = extras.getInt(EXTRA_EVENT_ID);
            }
        } else {
            eventId = (int) savedInstanceState.getSerializable(EXTRA_EVENT_ID);
        }

        return eventId;
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}





